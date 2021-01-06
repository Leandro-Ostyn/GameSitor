package be.vives.gamesitor.mainGame

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.databinding.MainGameFragmentBinding
import be.vives.gamesitor.models.PlayerLevelHelper


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

        val playerLevelHelper = PlayerLevelHelper()
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

            mainGameViewmodel.player.observe(viewLifecycleOwner, {
                val level = playerLevelHelper.getLevelFromExperience(it.character.exp)
                binding.lblLevel.text = "Level: $level"
                val expleft =
                    playerLevelHelper.getRemainingExperienceUntilNextLevel(it.character.exp)
                binding.lblexpLeft.text = "exp for level up: $expleft"
                val expNextLevel = playerLevelHelper.getExperienceFromLevel(level + 1)
                binding.progressBarlvl.max = expNextLevel
                binding.progressBarlvl.progress = it.character.exp.toInt()

            })
            return root
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
               requireActivity().moveTaskToBack(true)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }
}

