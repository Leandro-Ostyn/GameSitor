package be.vives.gamesitor.equipmentList

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
import be.vives.gamesitor.constants.EQUIP
import be.vives.gamesitor.databinding.EquipmentListFragmentBinding

class EquipmentListFragment : Fragment() {

    private val equipmentListViewModel: EquipmentListViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        val type = EquipmentListFragmentArgs.fromBundle(requireArguments()).type
        ViewModelProvider(
            this,
            EquipmentListViewModel.EquipmentListFactory(type, activity.application)
        ).get(
            EquipmentListViewModel::class.java
        )
    }
    private lateinit var binding: EquipmentListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.equipment_list_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = equipmentListViewModel

        binding.rvEquipmentList.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            equipmentListViewModel.displayItemDetails(it)
        })
        equipmentListViewModel.equipmentItems.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                binding.lblEquipment.visibility = View.VISIBLE
            }
        })
        equipmentListViewModel.navigateToSelectedItem.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(
                    EquipmentListFragmentDirections.actionEquipmentListFragmentToDetailFragment(
                        it.itemId, EQUIP
                    )
                )
                equipmentListViewModel.displayItemDetailsComplete()
            }
        })
        return binding.root
    }

}