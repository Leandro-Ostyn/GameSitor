package be.vives.gamesitor.equipment

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.constants.getDatabase
import be.vives.gamesitor.constants.getRepository
import be.vives.gamesitor.models.Item
import be.vives.gamesitor.models.Player
import be.vives.gamesitor.models.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewHeroViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = getRepository(database)
    private val _player = repository.player
    val player: LiveData<Player> get() = _player
    private val _weaponList = repository.playerWeaponList
    val weaponList: LiveData<List<Item>> get() = _weaponList
    private val _weapon2HList = repository.player2HWeaponList
    val weapon2HList: LiveData<List<Item>> get() = _weapon2HList
    private val _helmetList = repository.playerHelmetList
    val helmetList: LiveData<List<Item>> get() = _helmetList
    private val _bodyList = repository.playerBodyList
    val bodyList: LiveData<List<Item>> get() = _bodyList
    private val _legsList = repository.playerLegsList
    val legsList: LiveData<List<Item>> get() = _legsList
    private val _shieldList = repository.playerShieldList
    val shieldList: LiveData<List<Item>> get() = _shieldList
    private val _bootsList = repository.playerBootsList
    val bootsList: LiveData<List<Item>> get() = _bootsList
    private val _types = repository.types
    val types: LiveData<List<Type>> get() = _types

    fun checkInventoryItemsOnChosenType(
        inventoryList: List<Item>,
        type: Type
    ): MutableList<Item> {

        val filteredList = mutableListOf<Item>()
        for (inventoryItem in inventoryList) {
            filteredList.addAll(type.items.filter { item -> item.itemId == inventoryItem.itemId })
        }
        return filteredList
    }

    fun setEquipmentListForType(itemList: List<Item>, type: String) {
        viewModelScope.launch(Dispatchers.IO) {
        repository.setFilteredListForEquipment(itemList, type)}
    }

    class ViewHeroViewModelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ViewHeroViewModel::class.java)) {
                return ViewHeroViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}