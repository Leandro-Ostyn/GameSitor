package be.vives.gamesitor.mainGame

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.database.getDatabase
import be.vives.gamesitor.repository.Repository
import kotlinx.coroutines.launch

enum class SitorApiStatus { LOADING, ERROR, DONE }
class MainGameViewmodel(app: Application) : AndroidViewModel(app) {
    private val database = getDatabase(app)
    private val repository = Repository(database)

    //Checking Status of Api Connection
    private val _status = MutableLiveData<SitorApiStatus>()
    val status: LiveData<SitorApiStatus>
        get() = _status

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = Transformations.map(status) {
        _progress.value = _progress.value!!.plus(1)
        return@map _progress.value

    }

    init {
        _progress.value = 0
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshBackgrounds()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshCategories()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshCategoryTypes()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshCharacterList()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshEffects()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshEffectLists()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshEquipmentList()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshEquipmentItemsList()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshInventoryList()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshInventoryItemsList()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshItems()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshRewards()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshShops()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshStage()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshStats()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshStocks()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshTypes()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshTypeItems()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }

        viewModelScope.launch {
            try {
                _status.postValue(SitorApiStatus.LOADING)
                repository.refreshPlayers()
                _status.postValue(SitorApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(SitorApiStatus.ERROR)
            }
        }
    }

    class MainGameViewmodelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainGameViewmodel::class.java)) {
                return MainGameViewmodel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}