package be.vives.gamesitor.loading

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.constants.WEAPON
import be.vives.gamesitor.databinding.LoadingFragmentBinding
import be.vives.gamesitor.models.*
import be.vives.gamesitor.network.NetworkConnection
import com.bumptech.glide.Glide
import timber.log.Timber
import java.lang.Exception

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

        loadingViewModel.gettingDataSuccess.observe(viewLifecycleOwner, { succes ->
            if (succes) {
                loadingViewModel.apply {
                    getItems().observe(viewLifecycleOwner, {
                        if (it != null) {
                            setItems(it)
                        }
                    })
                    getTypes().observe(viewLifecycleOwner, { listDbType ->
                        if (listDbType != null) {
                            items.observe(viewLifecycleOwner, { items ->
                                if (items != null) {
                                    getTypeItemCrossRef().observe(
                                        viewLifecycleOwner,
                                        { typeItemList ->
                                            setTypes(listDbType, typeItemList, items)
                                        })

                                }
                            })
                        }
                    })
                    getBackgrounds().observe(viewLifecycleOwner, {
                        setBackgrounds(it)
                    })
                    getStats().observe(viewLifecycleOwner, { dbstatslist ->
                        if (dbstatslist != null) {
                            setStats(dbstatslist)
                        }
                    })
                    items.observe(viewLifecycleOwner, {
                        if (it != null) {
                            getRewards().observe(viewLifecycleOwner, { dbRewards ->
                                if (dbRewards != null) {
                                    setRewards(dbRewards, it)
                                }
                            })
                            getEquipments().observe(viewLifecycleOwner, { dbEquipmentList ->
                                if (dbEquipmentList != null) {
                                    getEquipmentItemCrossRef().observe(viewLifecycleOwner,
                                        { equipmentItemCrossRefList ->
                                            if (equipmentItemCrossRefList != null) {
                                                setEquipments(
                                                    it,
                                                    equipmentItemCrossRefList,
                                                    dbEquipmentList
                                                )
                                            }

                                        })
                                }
                            })
                            getInventories().observe(viewLifecycleOwner, { dbInventoryList ->
                                if (dbInventoryList != null) {
                                    getInventoryCrosReffItems().observe(viewLifecycleOwner,
                                        { inventoryItemsCrossRef ->
                                            if (inventoryItemsCrossRef != null) {
                                                setInventories(
                                                    it,
                                                    dbInventoryList,
                                                    inventoryItemsCrossRef
                                                )
                                            }
                                        })
                                }
                            })
                        }
                    })
                    getCharacters().observe(viewLifecycleOwner, { dbCharacterList ->
                        if (dbCharacterList != null) {
                            equipments.observe(viewLifecycleOwner, { equipmentList ->
                                if (equipmentList != null) {
                                    statsList.observe(viewLifecycleOwner, { statsList ->
                                        if (statsList != null) {
                                            setCharacter(dbCharacterList, equipmentList, statsList)
                                        }
                                    })
                                }
                            })
                        }
                    })
                    getStages().observe(viewLifecycleOwner, { dbStageList ->
                        if (dbStageList != null) {
                            backgrounds.observe(viewLifecycleOwner, { backgroundList ->
                                if (backgroundList != null) {
                                    characters.observe(viewLifecycleOwner, { characterList ->
                                        if (characterList != null) {
                                            rewards.observe(viewLifecycleOwner, { rewardList ->
                                                if (rewardList != null) {
                                                    setStages(
                                                        dbStageList,
                                                        backgroundList,
                                                        characterList,
                                                        rewardList
                                                    )
                                                }
                                            })
                                        }
                                    })
                                }
                            })
                        }
                    })
                    dbPlayer.observe(viewLifecycleOwner, { dbPlayer ->
                        if (dbPlayer != null) {
                            characters.observe(viewLifecycleOwner, { characters ->
                                if (characters != null) {
                                    inventories.observe(viewLifecycleOwner, { inventoryList ->
                                        if (inventoryList != null) {
                                            setPlayer(dbPlayer, characters, inventoryList)
                                        }

                                    })
                                }
                            })
                        }
                    })
                }
            }
        })

        loadingViewModel.player.observe(viewLifecycleOwner, {
            if (it != null) {
                findNavController().navigate(LoadingFragmentDirections.actionLoadingFragmentToMainGameFragment())
            }
        })
        loadingViewModel.status.observe(viewLifecycleOwner,
            {
                if (it.name == "ERROR") {
                    Timber.i("something went wrong")
                }
            })
        return binding.root

    }
}