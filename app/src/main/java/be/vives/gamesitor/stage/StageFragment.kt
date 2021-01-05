package be.vives.gamesitor.stage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
                        stageViewModel.setFalse()
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
                        stageViewModel.setFalse()
                    }
                })
            }
        })
        binding.viewmodel = stageViewModel
        stageViewModel.apply {

            stages.observe(viewLifecycleOwner, { stageList ->
                if (stageList != null) {
                    player.observe(viewLifecycleOwner, {
                        prepareStage(stageList[it.progress], it)

                    })
                }
            })
            attacked.observe(viewLifecycleOwner, {
                if (it) {
                    binding.btnAttack.isEnabled = false
                    hpEnemy.observe(viewLifecycleOwner, { hpleft ->
                        if (hpleft > 0) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                defend()
                            }, 500)
                        }
                    })
                } else {
                    binding.btnAttack.isEnabled = true
                }

            })

            hitIsNull.observe(viewLifecycleOwner, {
                if (it) {
                    val toast = Toast.makeText(context, "The hit was evaded !", Toast.LENGTH_SHORT)
                    toast.setGravity(1, 200, 200)
                    toast.show()

                    hitIsNullShown()
                }
            })
            hpEnemy.observe(viewLifecycleOwner, { hpleft ->
                if (hpleft <= 0) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        gameWon()
                    }, 100)
                }
            })

            hpHero.observe(viewLifecycleOwner, { hpleft ->
                if (hpleft <= 0) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        gameLost()
                    }, 100)

                }
            })

        }


        return binding.root
    }
}
