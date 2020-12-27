package be.vives.gamesitor.equipment

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
import timber.log.Timber


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
            createItemListForType(WEAPON)
            viewHeroViewModel.weaponList.observe(viewLifecycleOwner, {
                if (it != null) {
                    findNavController().navigate(
                        ViewHeroFragmentDirections.actionViewHeroFragmentToEquipmentListFragment(
                            WEAPON
                        )
                    )
                }
            })
        }
        binding.btnBody.setOnClickListener() {
            createItemListForType(BODY)
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
            createItemListForType(HELMET)
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
            createItemListForType(SHIELD)
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
            createItemListForType(LEGS)
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
            createItemListForType(BOOTS)
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
        viewHeroViewModel.player.observe(viewLifecycleOwner, { databasePlayer ->
            if (databasePlayer != null) {
                viewHeroViewModel.getCrossRefEquipmentItems(databasePlayer.characterId)
                    .observe(viewLifecycleOwner, { equipmentItemsCrossRef ->
                        if (equipmentItemsCrossRef != null) {
                            viewHeroViewModel.getItemListForChosenList(
                                viewHeroViewModel.getItemIdsFromCrossReffEquipment(
                                    equipmentItemsCrossRef
                                )
                            ).observe(viewLifecycleOwner, {
                                if (it != null) {
                                    viewHeroViewModel.setEquippedItems(it)
                                }
                            })

                        }
                    })
            }
        })

        viewHeroViewModel.equippedItems.observe(viewLifecycleOwner, { itemList ->
            if (itemList != null) {
                viewHeroViewModel.weapon2HList.observe(viewLifecycleOwner, {
                    showEquippedItems(itemList, it, binding.btnWeapon)
                })
                viewHeroViewModel.weaponList.observe(viewLifecycleOwner, {
                    showEquippedItems(itemList, it, binding.btnWeapon)
                })
                viewHeroViewModel.helmetList.observe(viewLifecycleOwner, {
                    showEquippedItems(itemList, it, binding.btnHelmet)
                })
                viewHeroViewModel.bodyList.observe(viewLifecycleOwner, {
                    showEquippedItems(itemList, it, binding.btnBody)
                })
                viewHeroViewModel.shieldList.observe(viewLifecycleOwner, {
                    showEquippedItems(itemList, it, binding.btnShield)
                })
                viewHeroViewModel.legsList.observe(viewLifecycleOwner, {
                    showEquippedItems(itemList, it, binding.btnLegs)
                })
                viewHeroViewModel.bootsList.observe(viewLifecycleOwner, {
                    showEquippedItems(itemList, it, binding.btnBoots)
                })
            }
        })
        return binding.root
    }

    private fun showEquippedItems(
        equippedItemList: List<Item>,
        equipmentItems: List<Item>,
        imageView: ImageView
    ) {
        if (equipmentItems != null) {
            for (item in equipmentItems) {
                for (equippedItem in equippedItemList) {
                    if (equippedItem.itemId == item.itemId) {
                        Glide.with(imageView.context).load(equippedItem.image)
                            .centerCrop()
                            .into(imageView)
                    }
                }
            }
        }
    }

    private fun createItemListForType(type: String) {
        viewHeroViewModel.getType(type).observe(viewLifecycleOwner, { databaseType ->
            if (databaseType != null) {
                Timber.i(databaseType.type)
                viewHeroViewModel.getItemCrossRefInType(databaseType.typeId)
                    .observe(viewLifecycleOwner, {
                        Timber.i(it.toString())
                        if (it != null) {
                            val itemIds = viewHeroViewModel.getItemIdsFromCrossreff(it)
                            viewHeroViewModel.getItemListForChosenList(itemIds)
                                .observe(viewLifecycleOwner, { itemList ->
                                    Timber.i(itemList.toString())
                                    if (itemList != null) {
                                        viewHeroViewModel.inventoryList.observe(
                                            viewLifecycleOwner,
                                            { inventoryList ->
                                                if (inventoryList != null) {
                                                    Timber.i(inventoryList.toString())
                                                    val inventoryItemListFilteredOnType =
                                                        viewHeroViewModel.checkInventoryItemsOnChosenType(
                                                            inventoryList,
                                                            itemIds
                                                        )
                                                    viewHeroViewModel.setFilteredListForBagEquipment(
                                                        inventoryItemListFilteredOnType, type
                                                    )
                                                    Timber.i("completed the list Insert")
                                                }
                                            })
                                    }
                                })
                        }
                    })
            }
        })
    }
}
