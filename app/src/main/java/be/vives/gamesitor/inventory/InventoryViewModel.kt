package be.vives.gamesitor.inventory

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.constants.getDatabase
import be.vives.gamesitor.constants.getRepository
import be.vives.gamesitor.models.Item
import be.vives.gamesitor.models.Player

class InventoryViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = getRepository(database)
    private val _player = repository.player
    val player: LiveData<Player> get() = _player

    private val _navigateToSelectedItem = MutableLiveData<Item>()
    val navigateToSelectedItem: LiveData<Item>
        get() = _navigateToSelectedItem

    fun displayItemDetails(item: Item) {
        _navigateToSelectedItem.value = item
    }

    fun displayItemDetailsComplete() {
        _navigateToSelectedItem.value = null
    }


    class InventoryViewModelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
                return InventoryViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
