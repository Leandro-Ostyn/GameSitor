package be.vives.gamesitor.mainGame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.gamesitor.database.entities.DatabasePlayer
import be.vives.gamesitor.database.getDatabase
import be.vives.gamesitor.database.getRepository


class MainGameViewmodel(app: Application) : AndroidViewModel(app) {
    private val database = getDatabase(app)
    private val repository = getRepository(database)
    private val _networkConnection = repository.networkConnection
    val networkConnection: LiveData<Boolean> get() = _networkConnection
    private val _player = repository.dbPlayer
    val player: LiveData<DatabasePlayer> get() = _player

    fun setNetWorkConnection(value: Boolean) {
        repository.setNetwork(value)
    }


    class MainGameViewmodelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainGameViewmodel::class.java)) {
                return MainGameViewmodel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}