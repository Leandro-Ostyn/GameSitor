package be.vives.gamesitor.stage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import be.vives.gamesitor.R
import be.vives.gamesitor.databinding.StageFragmentBinding
import be.vives.gamesitor.models.*
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

        binding.btnAttack.setOnClickListener() {
            stageViewModel.attack()
        }
        stageViewModel.attacked.observe(viewLifecycleOwner, {
            if (it) {
                stageViewModel.defend()
            }
        })
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewmodel = stageViewModel
        stageViewModel.apply {

            getAllStages().observe(viewLifecycleOwner, { databaseStageList ->
                if (databaseStageList != null) {
                    val stageList = mutableListOf<Stage>()
                    var counter = 0
                    for (databasestageFromList in databaseStageList) {
                        getStage(databasestageFromList.stageId).observe(
                            viewLifecycleOwner,
                            { databaseStage ->
                                if (databaseStage != null) {
                                    getBackGroundFromStage(databaseStage.backgroundId)
                                        .observe(viewLifecycleOwner, { databaseBackground ->
                                            if (databaseBackground != null) {
                                                val background = Background(
                                                    backgroundId = databaseBackground.backgroundId,
                                                    name = databaseBackground.name,
                                                    image = databaseBackground.image
                                                )
                                                getRewardFromStage(databaseStage.rewardId)
                                                    .observe(viewLifecycleOwner, { databaseReward ->
                                                        if (databaseReward != null) {
                                                            getItemById(databaseReward.itemId)
                                                                .observe(
                                                                    viewLifecycleOwner,
                                                                    { item ->
                                                                        if (item != null) {
                                                                            val reward = Reward(
                                                                                exp = databaseReward.exp,
                                                                                rewardId = databaseReward.rewardId,
                                                                                item = item
                                                                            )
                                                                            getDatabaseCharacter(
                                                                                databaseStage.characterId
                                                                            )
                                                                                .observe(
                                                                                    viewLifecycleOwner,
                                                                                    { databaseCharacter ->
                                                                                        if (databaseCharacter != null) {
                                                                                            getStatsById(
                                                                                                databaseCharacter.statsId
                                                                                            )
                                                                                                .observe(
                                                                                                    viewLifecycleOwner,
                                                                                                    { databaseStats ->
                                                                                                        if (databaseStats != null) {
                                                                                                            val stats =
                                                                                                                Stats(
                                                                                                                    attack = databaseStats.attack,
                                                                                                                    defence = databaseStats.defence,
                                                                                                                    strength = databaseStats.strength,
                                                                                                                    lifepoints = databaseStats.lifepoints,
                                                                                                                    statsId = databaseStats.statsId
                                                                                                                )

                                                                                                            getEquipmentByCharacterId(
                                                                                                                databaseCharacter.characterId
                                                                                                            ).observe(
                                                                                                                viewLifecycleOwner,
                                                                                                                { databaseEquipment ->
                                                                                                                    if (databaseEquipment != null) {
                                                                                                                        getCrossRefEquipmentItems(
                                                                                                                            databaseEquipment.equipmentId
                                                                                                                        )
                                                                                                                            .observe(
                                                                                                                                viewLifecycleOwner,
                                                                                                                                { EquipmentItemsList ->
                                                                                                                                    if (EquipmentItemsList != null) {
                                                                                                                                        stageViewModel.getItemListForChosenList(
                                                                                                                                            stageViewModel.getItemIdsFromCrossReffEquipment(
                                                                                                                                                EquipmentItemsList
                                                                                                                                            )
                                                                                                                                        )
                                                                                                                                            .observe(
                                                                                                                                                viewLifecycleOwner,
                                                                                                                                                {
                                                                                                                                                    if (it != null) {
                                                                                                                                                        val equipment =
                                                                                                                                                            Equipment(
                                                                                                                                                                equipmentId = databaseEquipment.equipmentId,
                                                                                                                                                                name = databaseEquipment.name,
                                                                                                                                                                items = it,
                                                                                                                                                                characterId = databaseCharacter.characterId
                                                                                                                                                            )
                                                                                                                                                        val character =
                                                                                                                                                            Character(
                                                                                                                                                                characterId = databaseCharacter.characterId,
                                                                                                                                                                name = databaseCharacter.name!!,
                                                                                                                                                                stats = stats,
                                                                                                                                                                equipment = equipment,
                                                                                                                                                                exp = databaseCharacter.exp,
                                                                                                                                                                IsHero = databaseCharacter.isHero,
                                                                                                                                                                image = databaseCharacter.image
                                                                                                                                                            )
                                                                                                                                                        val stage =
                                                                                                                                                            Stage(
                                                                                                                                                                stageId = databaseStage.stageId,
                                                                                                                                                                reward = reward,
                                                                                                                                                                background = background,
                                                                                                                                                                name = databaseStage.name,
                                                                                                                                                                character = character
                                                                                                                                                            )
                                                                                                                                                        stageList.add(
                                                                                                                                                            stage
                                                                                                                                                        )
                                                                                                                                                        counter++
                                                                                                                                                        if (counter == (databaseStageList.size)) {
                                                                                                                                                            Timber.i(
                                                                                                                                                                stageList.toString()
                                                                                                                                                            )
                                                                                                                                                            stageList.sortBy { stage -> stage.stageId }
                                                                                                                                                            setStageList(
                                                                                                                                                                stageList
                                                                                                                                                            )
                                                                                                                                                        }
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
                                                                        }
                                                                    })
                                                        }
                                                    })
                                            }
                                        })
                                }
                            })
                    }

                }
            })

            stages.observe(viewLifecycleOwner, { stageList ->
                if (stageList != null) {
                    prepareStage(stageList[2])
                }

            })
            attacked.observe(viewLifecycleOwner, Observer {
                if (it) {

                    Handler(Looper.getMainLooper()).postDelayed({
                        defend()
                    }, 1000)

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
