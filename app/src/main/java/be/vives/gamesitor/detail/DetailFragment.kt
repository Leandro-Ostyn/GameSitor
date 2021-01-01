package be.vives.gamesitor.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.constants.BUY
import be.vives.gamesitor.constants.SELL
import be.vives.gamesitor.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        val itemId = DetailFragmentArgs.fromBundle(requireArguments()).itemId
        val action = DetailFragmentArgs.fromBundle(requireArguments()).actionForItem
        val activity = requireNotNull(this.activity) {}
        detailViewModel = ViewModelProvider(
            this,
            DetailViewModel.DetailViewModelFactory(itemId, action, activity.application)
        ).get(DetailViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = detailViewModel

        detailViewModel.apply {
            selectedItem.observe(viewLifecycleOwner, {
                for (effect in it.effects) {
                    val textView = TextView(context)
                    textView.text =
                        "this boosts ${effect.attribute} with ${effect.value}"
                    binding.layoutForEffects.addView(textView)
                }
            })


            binding.btnAction.setOnClickListener {

                    actionForItem.observe(viewLifecycleOwner, { String ->
                        selectedItem.observe(viewLifecycleOwner, { selectedItem ->
                            if (selectedItem != null) {
                                when (String) {
                                    BUY -> {
                                        player.observe(viewLifecycleOwner, { player ->
                                            if (player != null) {
                                                if (player.coins >= selectedItem.cost) {
                                                    player.inventory.items.add(selectedItem)
                                                    player.coins -= selectedItem.cost
                                                    buySelectedItem(
                                                        itemId, player
                                                    )
                                                } else {
                                                    showPrompt(
                                                        "not enough money",
                                                        "you don't have enough coins to buy this item"
                                                    )

                                                }
                                            }
                                        })
                                    }
                                    SELL -> {
                                        player.observe(viewLifecycleOwner, { player ->
                                            if (player != null) {
                                                if (player.character.equipment.items.remove(
                                                        selectedItem
                                                    )
                                                ) {
                                                    removeEquippedItem(
                                                        player.character.equipment.equipmentId,
                                                        selectedItem.itemId
                                                    )
                                                }
                                                player.inventory.items.remove(selectedItem)
                                                player.coins += selectedItem.cost
                                                sellSelectedItem(
                                                    itemId, player
                                                )
                                            }
                                        })
                                    }
                                    else -> {
                                        player.observe(viewLifecycleOwner, { player ->
                                            if (player != null) {
                                                types.observe(
                                                    viewLifecycleOwner,
                                                    { types ->
                                                        if (types != null) {
                                                            val deleteItem =
                                                                checkEquipment(
                                                                    player,
                                                                    selectedItem,
                                                                    types
                                                                )
                                                            if (deleteItem != null) {
                                                                player.character.equipment.items.remove(
                                                                    deleteItem
                                                                )
                                                                deleteSelectedItemFromEquipment(
                                                                    deleteItem,
                                                                    player
                                                                )
                                                            }
                                                            player.character.equipment.items.add(
                                                                selectedItem
                                                            )
                                                            equipSelectedItem(
                                                                itemId,
                                                                player
                                                            )
                                                        }
                                                    })
                                            }
                                        })
                                    }
                                }
                            }
                        })
                    })
                }

            soldSelectedItem.observe(viewLifecycleOwner, {
                if (it) {
                    findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToBagFragment())
                    setfalse()
                }
            })
            boughtSelectedItem.observe(viewLifecycleOwner, {
                if (it) {
                    findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToShopFragment())
                    setfalse()
                }
            })
            equippedSelectedItem.observe(viewLifecycleOwner, {
                if (it) {
                    findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToViewHeroFragment())
                }
            })
        }
        return binding.root
    }

    private fun showPrompt(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            "confirm"
        ) { _, _ ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}

