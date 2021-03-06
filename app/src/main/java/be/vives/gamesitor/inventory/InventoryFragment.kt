package be.vives.gamesitor.inventory

import be.vives.gamesitor.adapters.PhotoGridAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.constants.SELL
import be.vives.gamesitor.databinding.InventoryFragmentBinding



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
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.inventory_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = inventoryViewModel

        binding.RVInventory.adapter =
            PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
                inventoryViewModel.displayItemDetails(it)
            })

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
        inventoryViewModel.player.observe(viewLifecycleOwner,{
            if (it.inventory.items.isEmpty()){
                binding.lblInventory.visibility= View.VISIBLE
            }
        })
        binding.btnMain.setOnClickListener{
            findNavController().navigate(InventoryFragmentDirections.actionBagFragmentToMainGameFragment())
        }


        return binding.root
    }


}
