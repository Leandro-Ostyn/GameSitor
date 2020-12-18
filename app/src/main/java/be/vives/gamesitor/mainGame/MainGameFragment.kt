package be.vives.gamesitor.mainGame

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
import be.vives.gamesitor.databinding.MainGameFragmentBinding
import timber.log.Timber

class MainGameFragment : Fragment() {

    private val mainGameViewmodel: MainGameViewmodel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(
            this,
            MainGameViewmodel.MainGameViewmodelFactory(activity.application)
        ).get(
            MainGameViewmodel::class.java
        )
    }
    private lateinit var binding: MainGameFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainGameViewmodel.progress.observe(viewLifecycleOwner, Observer {
            Timber.i("this is how many are completed $it of the 18")
        })
        binding = DataBindingUtil.inflate(inflater, R.layout.main_game_fragment, container, false)
        // Inflate the layout for this fragment
        binding.btnBattle.setOnClickListener {
            findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToStageFragment())
        }
        binding.btnEquipment.setOnClickListener {
            findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToViewHeroFragment())
        }
        binding.btnInventory.setOnClickListener {
            findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToBagFragment())
        }
        binding.btnShop.setOnClickListener {
            findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToShopFragment())
        }
        return binding.root
    }

}




