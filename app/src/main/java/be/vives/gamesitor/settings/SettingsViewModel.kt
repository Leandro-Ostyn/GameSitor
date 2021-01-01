package be.vives.gamesitor.settings

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.*
import be.vives.gamesitor.constants.getDatabase
import be.vives.gamesitor.constants.getRepository
import be.vives.gamesitor.database.entities.DatabaseSettings
import be.vives.gamesitor.models.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = getRepository(database)
    private val _settings = repository.settings
    val settings: LiveData<DatabaseSettings> get() = _settings
    private val _player = repository.player
    val player: LiveData<Player> get() = _player


    fun updateSettings(settings: DatabaseSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSettings(settings)
        }
    }

    fun updatePlayer(player: Player){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePlayer(player)
        }
    }


class SettingsViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
}