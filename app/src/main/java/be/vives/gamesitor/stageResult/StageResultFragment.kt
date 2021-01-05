package be.vives.gamesitor.stageResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.constants.DEFEAT
import be.vives.gamesitor.constants.VICTORY
import be.vives.gamesitor.databinding.StageResultFragmentBinding
import timber.log.Timber

@Suppress("DEPRECATION")
class StageResultFragment : Fragment() {

    private val stageResultViewModel: StageResultViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        val rewardId = StageResultFragmentArgs.fromBundle(requireArguments()).rewardId
        ViewModelProvider(
            this,
            StageResultViewModel.StageResultViewModelProvider(activity.application, rewardId)
        ).get(
            StageResultViewModel::class.java
        )
    }
    private lateinit var binding: StageResultFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.stage_result_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = stageResultViewModel
        val stageResult = StageResultFragmentArgs.fromBundle(requireArguments()).StageWon
        if (stageResult) {
            binding.background.setBackgroundResource(R.drawable.victory)
            binding.lblResult.text = VICTORY
            binding.lblResult.setTextColor(resources.getColor(R.color.defeatBlue))
        } else {
            binding.background.setBackgroundResource(R.drawable.defeated)

            binding.lblResult.text = DEFEAT
            binding.lblResult.setTextColor(resources.getColor(R.color.victoryRed))

        }
        val rewardId = StageResultFragmentArgs.fromBundle(requireArguments()).rewardId
        stageResultViewModel.setGameWon(stageResult)
        stageResultViewModel.rewards.observe(viewLifecycleOwner, { list ->
            if (list != null) {
                val reward = list.first { reward -> reward.rewardId == rewardId }
                stageResultViewModel.gameWon.observe(viewLifecycleOwner, { gameWon ->
                    if (gameWon != null) {
                        stageResultViewModel.setStageReward(reward, gameWon)
                    }
                    if (gameWon) {
                        stageResultViewModel.player.observe(viewLifecycleOwner, {
                            if (it != null) {
                                stageResultViewModel.stages.observe(viewLifecycleOwner,
                                    { stageList ->
                                        if (stageList.isNotEmpty()) {
                                            stageResultViewModel.progressUpdated.observe(
                                                viewLifecycleOwner,
                                                { isUpdate ->
                                                    if (!isUpdate) {
                                                        stageResultViewModel.setProgress(
                                                            it,
                                                            stageList.size
                                                        )
                                                    }
                                                })
                                        }
                                    })
                            }
                        })
                    }
                })

            }
        })

        binding.btnClaim.setOnClickListener {
            stageResultViewModel.stageReward.observe(viewLifecycleOwner, { reward ->
                if (reward != null) {
                    stageResultViewModel.player.value?.let { it1 ->
                        stageResultViewModel.insertRewardToPlayer(
                            it1, reward
                        )
                    }
                }
            })
        }

        stageResultViewModel.insertedReward.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(StageResultFragmentDirections.actionStageResultFragmentToMainGameFragment())
                stageResultViewModel.setFalse()
            }
        })

        return binding.root
    }


}