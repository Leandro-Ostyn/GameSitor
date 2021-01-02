package be.vives.gamesitor.equipment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.constants.*
import be.vives.gamesitor.databinding.ViewHeroFragmentBinding
import be.vives.gamesitor.models.Item
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber
import java.util.*


class ViewHeroFragment : Fragment() {
    private lateinit var binding: ViewHeroFragmentBinding
    private val viewHeroViewModel: ViewHeroViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(
            this,
            ViewHeroViewModel.ViewHeroViewModelFactory(activity.application)
        ).get(
            ViewHeroViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.view_hero_fragment, container, false)
        binding.lifecycleOwner = this
        createItemListForType(WEAPON)
        createItemListForType(H2WEAPON)
        createItemListForType(BODY)
        createItemListForType(HELMET)
        createItemListForType(SHIELD)
        createItemListForType(LEGS)
        createItemListForType(BOOTS)
        binding.btnWeapon.setOnClickListener() {
            viewHeroViewModel.weaponList.observe(viewLifecycleOwner, {
                if (it != null) {
                            showWeaponPrompt("choose your weapon", "in this prompt you can choose what kind of weapons u want to equip")
                }
            })
        }
        binding.btnBody.setOnClickListener() {
            viewHeroViewModel.bodyList.observe(viewLifecycleOwner, {
                if (it != null) {
                    findNavController().navigate(
                        ViewHeroFragmentDirections.actionViewHeroFragmentToEquipmentListFragment(
                            BODY
                        )
                    )
                }
            })
        }
        binding.btnHelmet.setOnClickListener() {
            viewHeroViewModel.helmetList.observe(viewLifecycleOwner, {
                if (it != null) {
                    findNavController().navigate(
                        ViewHeroFragmentDirections.actionViewHeroFragmentToEquipmentListFragment(
                            HELMET
                        )
                    )
                }
            })
        }
        binding.btnShield.setOnClickListener() {
            viewHeroViewModel.shieldList.observe(viewLifecycleOwner, {
                if (it != null) {
                    findNavController().navigate(
                        ViewHeroFragmentDirections.actionViewHeroFragmentToEquipmentListFragment(
                            SHIELD
                        )
                    )
                }
            })
        }
        binding.btnLegs.setOnClickListener() {
            viewHeroViewModel.legsList.observe(viewLifecycleOwner, {
                if (it != null) {
                    findNavController().navigate(
                        ViewHeroFragmentDirections.actionViewHeroFragmentToEquipmentListFragment(
                            LEGS
                        )
                    )
                }
            })
        }
        binding.btnBoots.setOnClickListener() {
            viewHeroViewModel.bootsList.observe(viewLifecycleOwner, {
                if (it != null) {
                    findNavController().navigate(
                        ViewHeroFragmentDirections.actionViewHeroFragmentToEquipmentListFragment(
                            BOOTS
                        )
                    )
                }
            })
        }
        binding.btnMainMenuHero.setOnClickListener() {
            findNavController().navigate(ViewHeroFragmentDirections.actionViewHeroFragmentToMainGameFragment())
        }

        binding.imgHero.setOnClickListener {
            findNavController().navigate(ViewHeroFragmentDirections.actionViewHeroFragmentToStatsFragment())
        }
        viewHeroViewModel.apply {
            player.observe(viewLifecycleOwner, {player ->
                if (player != null) {
                    Glide.with(binding.imgHero.context).load(player.character.image)
                        .into(binding.imgHero)
                    weapon2HList.observe(viewLifecycleOwner, {
                        showEquippedItem(player.character.equipment.items, it, binding.btnWeapon)
                    })
                    weaponList.observe(viewLifecycleOwner, {
                        showEquippedItem(player.character.equipment.items, it, binding.btnWeapon)
                    })
                    helmetList.observe(viewLifecycleOwner, {
                        showEquippedItem(player.character.equipment.items, it, binding.btnHelmet)
                    })
                    bodyList.observe(viewLifecycleOwner, {
                        showEquippedItem(player.character.equipment.items, it, binding.btnBody)
                    })
                    shieldList.observe(viewLifecycleOwner, {
                        showEquippedItem(player.character.equipment.items, it, binding.btnShield)
                    })
                    legsList.observe(viewLifecycleOwner, {
                        showEquippedItem(player.character.equipment.items, it, binding.btnLegs)
                    })
                    bootsList.observe(viewLifecycleOwner, {
                        showEquippedItem(player.character.equipment.items, it, binding.btnBoots)
                    })
                }
            })
        }
        return binding.root
    }

    private fun showEquippedItem(
        equippedItemList: List<Item>,
        equipmentItems: List<Item>,
        imageView: ImageView
    ) {
        for (item in equipmentItems) {
            for (equippedItem in equippedItemList) {
                if (equippedItem.itemId == item.itemId) {
                    Glide.with(imageView.context).load(equippedItem.image)
                        .into(imageView)
                }
            }
        }
    }

    private fun createItemListForType(type: String) {
        viewHeroViewModel.apply {
            player.observe(viewLifecycleOwner, { player ->
                if (player != null) {
                    types.observe(viewLifecycleOwner, { types ->
                        if (types != null) {
                            setEquipmentListForType(
                                checkInventoryItemsOnChosenType(
                                    player.inventory.items,
                                    types.first { filterType -> filterType.type == type }), type
                            )
                        }
                    })
                }
            })
        }
    }

    private fun showWeaponPrompt(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            "Weapons"
        ) { _, _ ->
            findNavController().navigate(
                ViewHeroFragmentDirections.actionViewHeroFragmentToEquipmentListFragment( WEAPON))
            }
            builder.setNegativeButton(
               "2H Weapons"
            ) { _, _ ->
                findNavController().navigate(
                    ViewHeroFragmentDirections.actionViewHeroFragmentToEquipmentListFragment( H2WEAPON))
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}

