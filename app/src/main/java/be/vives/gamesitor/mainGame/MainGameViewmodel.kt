package be.vives.gamesitor.mainGame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.gamesitor.constants.getDatabase
import be.vives.gamesitor.constants.getRepository
import be.vives.gamesitor.database.entities.DatabaseSettings
import be.vives.gamesitor.models.Player


class MainGameViewmodel(app: Application) : AndroidViewModel(app) {
    private val database = getDatabase(app)
    private val repository = getRepository(database)
    private val _player = repository.player
    val player: LiveData<Player> get() = _player
    private val _settings = repository.settings
    val settings : LiveData<DatabaseSettings> get() = _settings


    class MainGameViewmodelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainGameViewmodel::class.java)) {
                return MainGameViewmodel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}