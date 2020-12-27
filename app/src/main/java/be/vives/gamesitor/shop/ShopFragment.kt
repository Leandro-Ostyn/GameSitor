package be.vives.gamesitor.shop

import PhotoGridAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.databinding.ShopFragmentBinding
import be.vives.gamesitor.detail.BUY


class ShopFragment : Fragment() {
    private val shopViewmodel: ShopViewmodel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(this, ShopViewmodel.ShopViewmodelFactory(activity.application)).get(
            ShopViewmodel::class.java
        )
    }
    private lateinit var binding: ShopFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.shop_fragment, container, false)

        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewmodel = shopViewmodel

        binding.RVShop.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            shopViewmodel.displayItemDetails(it)
        })

        shopViewmodel.navigateToSelectedItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    ShopFragmentDirections.actionShopFragmentToDetailFragment(
                        it.itemId, BUY
                    )
                )
                shopViewmodel.displayItemDetailsComplete()
            }
        })

        return binding.root
    }


}
