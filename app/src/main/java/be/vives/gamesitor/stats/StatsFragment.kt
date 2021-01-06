package be.vives.gamesitor.stats

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.constants.*
import be.vives.gamesitor.databinding.StatsFragmentBinding
import be.vives.gamesitor.models.Stats
import timber.log.Timber

class StatsFragment : Fragment() {

    private val statsViewModel: StatsViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(this, StatsViewModel.StatsViewModelFactory(activity.application)).get(
            StatsViewModel::class.java
        )
    }
    lateinit var binding: StatsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.stats_fragment, container, false)
        binding.viewmodel = statsViewModel
        binding.lifecycleOwner = this
        statsViewModel.player.observe(viewLifecycleOwner, { player ->
            if (player != null) {
                binding.btnAddAttack.setOnClickListener {
                    statsViewModel.updateStatusPoints(ATTACK, ADD, player)
                }
                binding.btnAddDefence.setOnClickListener {
                    statsViewModel.updateStatusPoints(DEFENCE, ADD, player)
                }
                binding.btnAddHp.setOnClickListener {
                    statsViewModel.updateStatusPoints(HITPOINTS, ADD, player)
                }
                binding.btnAddStrength.setOnClickListener {
                    statsViewModel.updateStatusPoints(STRENGTH, ADD, player)
                }

                binding.btnRemoveAttack.setOnClickListener {
                    statsViewModel.updateStatusPoints(ATTACK, REMOVE, player)
                }
                binding.btnRemoveDef.setOnClickListener {
                    statsViewModel.updateStatusPoints(
                        DEFENCE,
                        REMOVE,
                        player
                    )
                }
                binding.btnRemoveHp.setOnClickListener {
                    statsViewModel.updateStatusPoints(
                        HITPOINTS,
                        REMOVE,
                        player
                    )
                }
                binding.btnRemoveStr.setOnClickListener {
                    statsViewModel.updateStatusPoints(
                        STRENGTH,
                        REMOVE,
                        player
                    )
                }
                binding.btnEquipmentStats.setOnClickListener {
                    findNavController().navigate(StatsFragmentDirections.actionStatsFragmentToViewHeroFragment())
                }

            }
        })

        return binding.root
    }

}