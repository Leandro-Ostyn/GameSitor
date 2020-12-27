package be.vives.gamesitor.detail

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.databinding.FragmentDetailBinding
import timber.log.Timber

const val BUY = "Buy"
const val SELL = "Sell"

class DetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        val itemid = DetailFragmentArgs.fromBundle(requireArguments()).itemId
        val action = DetailFragmentArgs.fromBundle(requireArguments()).actionForItem
        val activity = requireNotNull(this.activity) {}
        detailViewModel = ViewModelProvider(
            this,
            DetailViewModel.DetailViewModelFactory(itemid, action, activity.application)
        ).get(DetailViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = detailViewModel

        detailViewModel.selectedItem.observe(viewLifecycleOwner, {
            for (effect in it.effects) {
                val textview = TextView(context)
                textview.text =
                    "this boosts ${effect.attribute} with ${effect.value}"
                binding.layoutForEffects.addView(textview)
            }
        })
        binding.btnAction.setOnClickListener {
            detailViewModel.actionForItem.observe(viewLifecycleOwner, { String ->
                if (String == BUY) {
                    detailViewModel.player.observe(viewLifecycleOwner, { databasePlayer ->
                        if (databasePlayer != null) {
                            detailViewModel.buySelectedItem(itemid, databasePlayer.inventoryId)
                        }
                    })
                } else if (String == SELL) {
                    detailViewModel.player.observe(viewLifecycleOwner, { databasePlayer ->
                        if (databasePlayer != null) {
                            detailViewModel.sellSelectedItem(itemid, databasePlayer.inventoryId)
                        }
                    })
                } else {
                    detailViewModel.player.observe(viewLifecycleOwner, { databasePlayer ->
                        if (databasePlayer != null) {
                            detailViewModel.getEquipmentIdFromPlayer(databasePlayer.characterId).observe(viewLifecycleOwner,{
                                if (it!=null){
                                    detailViewModel.equipSelectedItem(itemid, it.equipmentId)
                                }
                            })
                        }
                    })
                }
            })
        }
        detailViewModel.soldSelectedItem.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToBagFragment())
                detailViewModel.setfalse()
            }
        })
        detailViewModel.boughtSelectedItem.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToShopFragment())
                detailViewModel.setfalse()
            }
        })
        detailViewModel.equippedSelectedItem.observe(viewLifecycleOwner,{
            if (it){
                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToViewHeroFragment())
            }
        })
        return binding.root
    }
}
