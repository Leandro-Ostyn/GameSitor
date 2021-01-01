package be.vives.gamesitor.mainGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.databinding.MainGameFragmentBinding

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
    ): View {


        binding = DataBindingUtil.inflate(inflater, R.layout.main_game_fragment, container, false)
        binding.lifecycleOwner = this
        binding.apply {
            viewmodel = mainGameViewmodel
            btnBattle.setOnClickListener {
                findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToStageFragment())
            }
            btnSettings.setOnClickListener {
                findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToSettingsFragment())
            }

            btnEquipment.setOnClickListener {
                findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToViewHeroFragment())
            }
            btnInventory.setOnClickListener {
                findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToBagFragment())
            }
            btnShop.setOnClickListener {
                findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToShopFragment())
            }
            return root
        }
    }
}

