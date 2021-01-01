package be.vives.gamesitor.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.database.entities.DatabasePlayer
import be.vives.gamesitor.databinding.LoadingFragmentBinding
import be.vives.gamesitor.models.Item
import be.vives.gamesitor.network.NetworkConnection
import com.bumptech.glide.Glide
import timber.log.Timber

class LoadingFragment : Fragment() {


    private val loadingViewModel: LoadingViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(
            this,
            LoadingViewModel.LoadingViewModelFactory(activity.application)
        ).get(
            LoadingViewModel::class.java
        )
    }
    private lateinit var binding: LoadingFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.loading_fragment, container, false)
        Glide.with(this).load(R.drawable.loadingscreen).into(binding.imageView6)
        val networkConnection = NetworkConnection(requireContext())

        networkConnection.observe(viewLifecycleOwner, {
            loadingViewModel.setNetWorkConnection(it)
            if (it) {
                try {
                    loadingViewModel.refreshAllDataFromApi()
                } catch (e: Exception) {
                    Timber.i("Error WITH API !")
                }

            } else {
                binding.imageView6.setImageResource(R.drawable.shopbackground)
            }
        })
        loadingViewModel.apply {
            gettingDataSuccess.observe(viewLifecycleOwner, { succes ->
                if (succes) {
                    Timber.i("fetching data was a succes")
                    registering.observe(viewLifecycleOwner, { registering ->
                        if (registering) {
                            dbPlayer.observe(viewLifecycleOwner, { databasePlayer ->
                                if (databasePlayer != null) {
                                    if (databasePlayer.characterId == "0") {
                                        createStatsForPlayerCharacter().observe(
                                            viewLifecycleOwner,
                                            { statsId ->
                                                if (statsId != "") {
                                                    Timber.i("fetching statsId was a succes")
                                                    dbCharacters.observe(viewLifecycleOwner,
                                                        { dbCharacterList ->
                                                            if (dbCharacterList != null) {
                                                                makeEquipmentForPlayerCharacter(
                                                                    databasePlayer
                                                                ).observe(viewLifecycleOwner,
                                                                    { equipmentId ->
                                                                        if (equipmentId != "") {
                                                                            if (equipmentIdMade.value == false) {
                                                                                Timber.i("creating equipmentId was a succes")
                                                                                makeCharacterForPlayer(
                                                                                    databasePlayer,
                                                                                    dbCharacterList,
                                                                                    statsId,
                                                                                    equipmentId
                                                                                ).observe(
                                                                                    viewLifecycleOwner,
                                                                                    {
                                                                                        equipmentUpdated.observe(
                                                                                            viewLifecycleOwner,
                                                                                            { updated ->
                                                                                                if (!updated) {
                                                                                                    updateEquipmentForPlayerCharacter(
                                                                                                        it,
                                                                                                        equipmentId
                                                                                                    )
                                                                                                    setDbPlayerInitializeDone()
                                                                                                    stopRegistering()
                                                                                                }
                                                                                            })

                                                                                    })
                                                                            }
                                                                        }
                                                                    })
                                                            }
                                                        })

                                                }
                                            })
                                    }
                                }
                            })
                        }
                        if (!registering){
                            setSettings(dbPlayer.value!!)
                            getStats().observe(viewLifecycleOwner,
                                { dbStats ->
                                    if (dbStats != null) {
                                        setStats(dbStats)
                                        Timber.i("set Stats was a succes")
                                    }
                                })
                            getBackgrounds().observe(viewLifecycleOwner,
                                { dbBackgrounds ->
                                    if (dbBackgrounds != null) {
                                        setBackgrounds(dbBackgrounds)
                                        Timber.i("set backgrounds was a succes")
                                    }
                                })
                            getItems().observe(viewLifecycleOwner, { items ->
                                if (items != null) {
                                    setItems(items)
                                    itemsAreSet.observe(viewLifecycleOwner, { itemsAreSet ->
                                        if (itemsAreSet) {
                                            Timber.i("set Items was a succes")
                                            setRewards(items)
                                            Timber.i("set Rewards was a succes")
                                            setEquipments(items)

                                            setInventories(items)
                                            Timber.i("set Inventories was a succes")
                                            setTypes(items)
                                            Timber.i("set Types was a succes")
                                        }
                                    })

                                }
                            })
                        }
                    })
                }
            })
            checkConditionToMakeCharacters().observe(viewLifecycleOwner, { matched ->
                if (matched) {
                    Timber.i("Condition is matched for characters")
                    setCharacters()
                }
            })
            checkConditionToMakeStages().observe(viewLifecycleOwner, { matched ->
                if (matched) {
                    Timber.i("Condition is matched for stages")
                    setStages()
                }

            })

            stagesAreSet.observe(viewLifecycleOwner, { stagesAreSet ->
                if (stagesAreSet) {
                    setDomainPlayer()
                }
            })
            domainPlayerIsSet.observe(viewLifecycleOwner, { domainPlayerIsSet ->
                if (domainPlayerIsSet) {
                    findNavController().navigate(LoadingFragmentDirections.actionLoadingFragmentToMainGameFragment())
                }
            })
        }

        return binding.root
    }
    //Functions to set db Data to domain models

    private fun setSettings(dbPlayer: DatabasePlayer) {
        loadingViewModel.apply {
            getSettingsByPlayerName(dbPlayer.name!!).observe(viewLifecycleOwner, {
                if (it != null) {
                    setSettings(it)
                } else {
                    createSettings(dbPlayer.name, dbPlayer.password!!)
                }
            })
        }

    }

    private fun setEquipments(itemList: List<Item>) {
        loadingViewModel.apply {
            getEquipmentItemCrossRef().observe(viewLifecycleOwner, { crossRefList ->
                if (crossRefList != null) {
                    getEquipments().observe(viewLifecycleOwner,
                        { dbEquipments ->
                            if (dbEquipments != null) {
                                setEquipments(itemList, crossRefList, dbEquipments)
                                Timber.i("set Equipment was a succes")
                            }
                        })
                }
            })
        }
    }

    private fun setRewards(itemList: List<Item>) {
        loadingViewModel.getRewards().observe(viewLifecycleOwner,
            { dbRewards ->
                if (dbRewards != null) {
                    loadingViewModel.setRewards(dbRewards, itemList)
                }
            })
    }

    private fun setInventories(itemList: List<Item>) {
        loadingViewModel.apply {
            getInventoryCrosReffItems().observe(viewLifecycleOwner, { crossRefList ->
                if (crossRefList != null) {
                    getInventories().observe(viewLifecycleOwner,
                        { dbInventories ->
                            if (dbInventories != null) {
                                setInventories(itemList, dbInventories, crossRefList)
                            }
                        })
                }
            })

        }
    }

    private fun setTypes(itemList: List<Item>) {
        loadingViewModel.apply {
            getTypeItemCrossRef().observe(viewLifecycleOwner, { crossRefList ->
                if (crossRefList != null) {
                    getTypes().observe(viewLifecycleOwner, { types ->
                        if (types != null) {
                            setTypes(types, crossRefList, itemList)
                        }
                    })
                }

            })
        }
    }

    private fun setCharacters() {
        loadingViewModel.apply {
            equipments.observe(viewLifecycleOwner, { equipmentList ->
                if (equipmentList != null) {
                    statsList.observe(viewLifecycleOwner, { statsList ->
                        if (statsList != null) {
                            getCharacters().observe(viewLifecycleOwner, { dbCharacters ->
                                if (dbCharacters != null) {
                                    setCharacter(dbCharacters, equipmentList, statsList)
                                    Timber.i("characters are Set")
                                }
                            })
                        }
                    })
                }
            })

        }
    }

    private fun setStages() {
        loadingViewModel.apply {
            backgrounds.observe(viewLifecycleOwner, { backgrounds ->
                if (backgrounds != null) {
                    characters.observe(viewLifecycleOwner, { characters ->
                        if (characters != null) {
                            rewards.observe(viewLifecycleOwner, { rewards ->
                                if (rewards != null) {
                                    getStages().observe(viewLifecycleOwner, { dbStageList ->
                                        if (dbStageList != null) {
                                            setStages(dbStageList, backgrounds, characters, rewards)
                                            Timber.i("Stages are set")
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

    private fun setDomainPlayer() {
        loadingViewModel.apply {
            setPlayer(dbPlayer.value!!, characters.value!!, inventories.value!!)


        }
    }

    //functions to check conditions
    private fun checkConditionToMakeCharacters(): MutableLiveData<Boolean> {
        val conditionsMatched = MutableLiveData<Boolean>()
        loadingViewModel.apply {
            equipmentsAreSet.observe(viewLifecycleOwner, { equipmentIsSet ->
                if (equipmentIsSet) {
                    statsAreSet.observe(viewLifecycleOwner, { statsIsSet ->
                        if (statsIsSet) {
                            conditionsMatched.postValue(true)
                        } else {
                            conditionsMatched.postValue(false)
                        }
                    })
                } else {
                    conditionsMatched.postValue(false)
                }
            })

        }
        return conditionsMatched
    }

    private fun checkConditionToMakeStages(): MutableLiveData<Boolean> {
        val conditionsMatched = MutableLiveData<Boolean>()
        loadingViewModel.apply {
            backgroundsAreSet.observe(viewLifecycleOwner, { backgroundIsSet ->
                if (backgroundIsSet) {
                    charactersAreSet.observe(viewLifecycleOwner, { charactersAreSet ->
                        if (charactersAreSet) {
                            rewardsAreSet.observe(viewLifecycleOwner, { rewardsAreSet ->
                                if (rewardsAreSet) {
                                    conditionsMatched.postValue(true)
                                } else {
                                    conditionsMatched.postValue(false)
                                }
                            })
                        } else {
                            conditionsMatched.postValue(false)
                        }
                    })
                } else {
                    conditionsMatched.postValue(false)
                }
            })
        }
        return conditionsMatched
    }

}