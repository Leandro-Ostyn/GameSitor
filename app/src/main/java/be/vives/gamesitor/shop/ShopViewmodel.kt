package be.vives.gamesitor.shop

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.database.getDatabase
import be.vives.gamesitor.repository.Repository
import be.vives.gamesitor.models.Item

class ShopViewmodel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = Repository(database)

    private val _items = repository.items
    val  items : LiveData<List<Item>> get() = _items


    private val _navigateToSelectedItem = MutableLiveData<Item>()
    val navigateToSelectedItem: LiveData<Item>
        get() = _navigateToSelectedItem

    fun displayPropertyDetails(item: Item) {
        _navigateToSelectedItem.value = item
    }

    fun displayPropertyDetailsComplete() {
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