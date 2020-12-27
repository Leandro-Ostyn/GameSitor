package be.vives.gamesitor.detail

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.database.entities.DatabaseEquipment
import be.vives.gamesitor.database.getDatabase
import be.vives.gamesitor.database.getRepository
import be.vives.gamesitor.models.Item
import kotlinx.coroutines.launch

class DetailViewModel(itemId: Int, actionForItem: String, app: Application) :
    AndroidViewModel(app) {
    private val database = getDatabase(app)
    private val repository = getRepository(database)
    private val _selectedItem = repository.getChosenItem(itemId)
    val selectedItem: LiveData<Item>
        get() = _selectedItem
    private val _actionForItem = MutableLiveData(actionForItem)
    val actionForItem: LiveData<String> get() = _actionForItem
    val player = repository.dbPlayer

    private val _boughtSelectedItem = MutableLiveData<Boolean>()
    val boughtSelectedItem: LiveData<Boolean>
        get() = _boughtSelectedItem

    private val _soldSelectedItem = MutableLiveData<Boolean>()
    val soldSelectedItem: LiveData<Boolean>
        get() = _soldSelectedItem

    private val _equippedSelectedItem = MutableLiveData<Boolean>()
    val equippedSelectedItem: LiveData<Boolean>
        get() = _equippedSelectedItem

    fun buySelectedItem(itemId: Int, inventoryId: Int) {
        viewModelScope.launch {
            repository.insertItemToInventory(itemId, inventoryId)
            _boughtSelectedItem.value = true
        }
    }

    fun sellSelectedItem(itemId: Int, inventoryId: Int) {
        viewModelScope.launch {
            repository.deleteItemFromInventory(inventoryId, itemId)
            _soldSelectedItem.value = true
        }
    }

    fun getEquipmentIdFromPlayer(characterId:Int): LiveData<DatabaseEquipment> {
         return   repository.getEquipmentId(characterId)

    }

    fun equipSelectedItem(itemId: Int, equipmentId : Int){

        viewModelScope.launch {
            repository.equipToPlayer(equipmentId, itemId)
            _equippedSelectedItem.value = true
        }
    }

    fun setfalse() {
        _boughtSelectedItem.value = false
        _soldSelectedItem.value = false
        _equippedSelectedItem.value= false
    }


    class DetailViewModelFactory(
        private val itemId: Int,
        private val actionForItem: String,
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(itemId, actionForItem, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
