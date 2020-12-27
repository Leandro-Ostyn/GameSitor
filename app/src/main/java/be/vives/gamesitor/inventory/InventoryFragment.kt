package be.vives.gamesitor.inventory

import PhotoGridAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.databinding.InventoryFragmentBinding
import be.vives.gamesitor.detail.SELL
import timber.log.Timber


class InventoryFragment : Fragment() {
    private val inventoryViewModel: InventoryViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(
            this,
            InventoryViewModel.InventoryViewModelFactory(activity.application)
        ).get(
            InventoryViewModel::class.java
        )
    }
    private lateinit var binding: InventoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.inventory_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = inventoryViewModel

        binding.RVInventory.adapter =
            PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
                inventoryViewModel.displayItemDetails(it)
            })
        inventoryViewModel.player.value?.let { databasePlayer ->
            inventoryViewModel.getItemsFromInventoryId(databasePlayer.inventoryId)
                .observe(viewLifecycleOwner, {
                    if (it != null)
                        inventoryViewModel.itemAsDomainModel(it).observe(viewLifecycleOwner, {
                            if (it != null) {
                                inventoryViewModel.setItems(it)
                            }
                        })
                }
                )
        }
        inventoryViewModel.navigateToSelectedItem.observe(viewLifecycleOwner,
            {
                it?.let {
                    findNavController().navigate(
                        InventoryFragmentDirections.actionBagFragmentToDetailFragment(
                            it.itemId, SELL
                        )
                    )
                    inventoryViewModel.displayItemDetailsComplete()
                }
            })


        return binding.root
    }


}
