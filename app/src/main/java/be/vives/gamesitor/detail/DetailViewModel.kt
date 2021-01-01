package be.vives.gamesitor.detail

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.constants.getDatabase
import be.vives.gamesitor.constants.getRepository
import be.vives.gamesitor.models.Item
import be.vives.gamesitor.models.Player
import be.vives.gamesitor.models.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(itemId: Int, actionForItem: String, app: Application) :
    AndroidViewModel(app) {
    private val database = getDatabase(app)
    private val repository = getRepository(database)
    private val _selectedItem = repository.getChosenItem(itemId)
    val selectedItem: LiveData<Item>
        get() = _selectedItem
    private val _actionForItem = MutableLiveData(actionForItem)
    val actionForItem: LiveData<String> get() = _actionForItem
    private val _player = repository.player
    val player: LiveData<Player> get() = _player
    private val _types = repository.types
    val types: LiveData<List<Type>> get() = _types

    private val _boughtSelectedItem = MutableLiveData<Boolean>()
    val boughtSelectedItem: LiveData<Boolean>
        get() = _boughtSelectedItem

    private val _soldSelectedItem = MutableLiveData<Boolean>()
    val soldSelectedItem: LiveData<Boolean>
        get() = _soldSelectedItem

    private val _equippedSelectedItem = MutableLiveData<Boolean>()
    val equippedSelectedItem: LiveData<Boolean>
        get() = _equippedSelectedItem

    fun buySelectedItem(itemId: Int, player: Player) {
        viewModelScope.launch {
            repository.insertItemToInventory(itemId, player)
            _boughtSelectedItem.value = true
        }
    }

    fun sellSelectedItem(itemId: Int, player: Player) {
        viewModelScope.launch {
            repository.deleteItemFromInventory(player, itemId)
            _soldSelectedItem.value = true
        }
    }

    fun removeEquippedItem(equipmentId: String, itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) { repository.removeEquippedItem(equipmentId, itemId) }
    }

    fun equipSelectedItem(itemId: Int, player: Player) {
        viewModelScope.launch {
            repository.equipToPlayer(itemId, player)
            _equippedSelectedItem.value = true
        }
    }

    fun deleteSelectedItemFromEquipment(deleteItem : Item,player: Player){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItemFromEquipment(deleteItem,player)
        }
    }

    fun setfalse() {
        _boughtSelectedItem.value = false
        _soldSelectedItem.value = false
        _equippedSelectedItem.value = false
    }


    fun checkEquipment(
        player: Player,
        selectedItem: Item,
        types: List<Type>
    ): Item? {

        var selectedItemType = ""
        for (type in types) {
            for (item in type.items) {
                if (item.itemId == selectedItem.itemId) {
                    selectedItemType = type.type.toString()
                }
            }
        }
        val typewithItems = types.first { type -> type.type == selectedItemType }
        for (item in player.character.equipment.items) {
            if (typewithItems.items.contains(item)) {
                return item
            }
        }
        return null
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
