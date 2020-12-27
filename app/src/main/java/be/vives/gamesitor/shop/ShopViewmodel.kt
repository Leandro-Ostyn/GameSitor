package be.vives.gamesitor.shop

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.database.entities.DatabasePlayer
import be.vives.gamesitor.database.getDatabase
import be.vives.gamesitor.database.getRepository
import be.vives.gamesitor.models.Item

open class ShopViewmodel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = getRepository(database)
    private val _player = repository.dbPlayer
    val player: LiveData<DatabasePlayer> get() = _player
    private val _items = repository.items
    val items: LiveData<List<Item>> get() = _items


    private val _navigateToSelectedItem = MutableLiveData<Item>()
    val navigateToSelectedItem: LiveData<Item>
        get() = _navigateToSelectedItem

    fun displayItemDetails(item: Item) {
        _navigateToSelectedItem.value = item
    }

    fun displayItemDetailsComplete() {
        _navigateToSelectedItem.value = null
    }


    class ShopViewmodelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShopViewmodel::class.java)) {
                return ShopViewmodel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}