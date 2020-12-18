package be.vives.gamesitor.detail

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


class DetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        val itemid = DetailFragmentArgs.fromBundle(requireArguments()).itemId
        val activity = requireNotNull(this.activity) {}
        detailViewModel = ViewModelProvider(
            this,
            DetailViewModel.DetailViewModelFactory(itemid, activity.application)
        ).get(DetailViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = detailViewModel

        detailViewModel.selectedItem.observe(viewLifecycleOwner, Observer {
            for (effect in it.effects) {
                val textview = TextView(context)
                textview.text =
                    "this boosts ${effect.attribute} with ${effect.value}"
                binding.layoutForEffects.addView(textview)
            }
        })
        binding.btnPurchase.setOnClickListener {
            detailViewModel.buySelectedItem(itemid)
        }


        detailViewModel.boughtSelectedItem.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToShopFragment())
           detailViewModel.setfalse()
            }
        })
        return binding.root
    }
}
