package be.vives.gamesitor.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.gamesitor.constants.getDatabase
import be.vives.gamesitor.constants.getRepository
import be.vives.gamesitor.database.entities.DatabaseSettings

class ActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = getRepository(database)

    private val _settings = repository.settings
    val settings: LiveData<DatabaseSettings> get() = _settings

    class ActivityViewModelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ActivityViewModel::class.java)) {
                return ActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}