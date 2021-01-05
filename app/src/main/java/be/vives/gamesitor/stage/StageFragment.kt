package be.vives.gamesitor.stage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.databinding.StageFragmentBinding


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
            binding.btnAttack.isEnabled = false
              moveHero(binding.ImgHero)
            Handler(Looper.getMainLooper()).postDelayed({
                stageViewModel.attack()
            }, 800)
        }
        binding.btnLeave.setOnClickListener {
            findNavController().navigate(StageFragmentDirections.actionStageFragmentToMainGameFragment())
        }

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

                    hpEnemy.observe(viewLifecycleOwner, { hpleft ->
                        if (hpleft > 0) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                moveEnemy(binding.imgEnemy)

                            }, 1000)
                            Handler(Looper.getMainLooper()).postDelayed({
                                defend()
                            }, 1800)
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
            gameLost.observe(viewLifecycleOwner, { lost ->
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
            gameWon.observe(viewLifecycleOwner, { won ->
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

        }
        return binding.root
    }
    private fun moveHero(imageView: ImageView) {
     imageView.animate().xBy(500f).yBy(-150f).withEndAction {
         imageView.animate().xBy(700f).yBy(-80f).withEndAction {
             imageView.animate().xBy(-700f).yBy(80f).withEndAction {
                 imageView.animate().xBy(-500f).yBy(150f)

             }
         }
     }
    }
    private fun moveEnemy(imageView: ImageView) {
        imageView.animate().xBy(-500f).yBy(-150f).withEndAction {
            imageView.animate().xBy(-700f).yBy(-80f).withEndAction {
                imageView.animate().xBy(700f).yBy(80f).withEndAction {
                    imageView.animate().xBy(500f).yBy(150f)
                }
            }
        }
    }
}

