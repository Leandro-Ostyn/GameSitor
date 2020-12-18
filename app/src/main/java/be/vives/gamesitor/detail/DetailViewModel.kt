package be.vives.gamesitor.detail

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.database.getDatabase
import be.vives.gamesitor.repository.Repository
import be.vives.gamesitor.models.Item

class DetailViewModel(itemId: Int, app: Application) : AndroidViewModel(app) {
    private val database = getDatabase(app)
    private val repository = Repository(database)
    private val _selectedItem = repository.getChosenItem(itemId)
    val selectedItem: LiveData<Item>
        get() = _selectedItem

    val player =repository.player

    private val _boughtSelectedItem = MutableLiveData<Boolean>()
    val boughtSelectedItem: LiveData<Boolean>
        get() = _boughtSelectedItem

    fun buySelectedItem(itemId: Int) {

        _boughtSelectedItem.value = repository.insertItemToInventory(itemId)
    }

    fun setfalse(){
        _boughtSelectedItem.value= false
    }

    class DetailViewModelFactory(
        private val itemId: Int,
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(itemId, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
