package be.vives.gamesitor.equipmentList

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.constants.getDatabase
import be.vives.gamesitor.constants.getRepository

import be.vives.gamesitor.models.Item

class EquipmentListViewModel(type: String, application: Application) :
    AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = getRepository(database)
    private val _equipmentItems = repository.returnChosenEquipmentList(type)
    val equipmentItems: LiveData<List<Item>> get() = _equipmentItems

    private val _navigateToSelectedItem = MutableLiveData<Item>()
    val navigateToSelectedItem: LiveData<Item>
        get() = _navigateToSelectedItem

    fun displayItemDetails(item: Item) {
        _navigateToSelectedItem.value = item
    }

    fun displayItemDetailsComplete() {
        _navigateToSelectedItem.value = null
    }

    class EquipmentListFactory(val type: String, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EquipmentListViewModel::class.java)) {
                return EquipmentListViewModel(type, app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}