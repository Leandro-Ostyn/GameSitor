package be.vives.gamesitor.stage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.databinding.StageFragmentBinding

import timber.log.Timber

class StageFragment : Fragment() {

    private val stageViewModel: StageViewmodel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(this, StageViewmodel.StageViewmodelFactory(activity.application)).get(
            StageViewmodel::class.java
        )
    }
    private lateinit var binding: StageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.stage_fragment, container, false)

        binding.lifecycleOwner = this

        binding.btnAttack.setOnClickListener() {
            stageViewModel.attack()
        }
        binding.btnLeave.setOnClickListener {
            findNavController().navigate(StageFragmentDirections.actionStageFragmentToMainGameFragment())
        }
        stageViewModel.attacked.observe(viewLifecycleOwner, {attacked->
            if (attacked==true) {
                Timber.i(attacked.toString())
                 stageViewModel.defend()
            }
        })

        stageViewModel.gameLost.observe(viewLifecycleOwner, { lost ->
            if (lost) {
                stageViewModel.settedStage.observe(viewLifecycleOwner, {
                    if (it != null) {
                        findNavController().navigate(
                            StageFragmentDirections.actionStageFragmentToStageResultFragment(
                                it.reward.rewardId,
                                false
                            )
                        )
                    }
                })
            }
        })

        stageViewModel.gameWon.observe(viewLifecycleOwner, { won ->
            if (won) {
                stageViewModel.settedStage.observe(viewLifecycleOwner, {
                    if (it != null) {
                        findNavController().navigate(
                            StageFragmentDirections.actionStageFragmentToStageResultFragment(
                                it.reward.rewardId,
                                won
                            )
                        )
                    }
                })
            }
        })
        binding.viewmodel = stageViewModel
        stageViewModel.apply {

            stages.observe(viewLifecycleOwner, { stageList ->
                if (stageList != null) {
                    player.observe(viewLifecycleOwner, {
                        prepareStage(stageList[0], it)

                    })
                }
            })
            attacked.observe(viewLifecycleOwner, Observer {
                if (it) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        defend()
                    }, 500)

                }
            })
            gameWon.observe(viewLifecycleOwner, Observer {
                if (it) {
                    Timber.i("i won ")
                }
            })
        }
        return binding.root
    }
}
