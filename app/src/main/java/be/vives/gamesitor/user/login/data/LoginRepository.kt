package be.vives.gamesitor.user.login.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.vives.gamesitor.database.GameSitorDatabase
import be.vives.gamesitor.database.entities.DatabasePlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LoginRepository(private val database: GameSitorDatabase) {

    private val _loggedIn = MutableLiveData<DatabasePlayer>()
    val loggedIn: LiveData<DatabasePlayer> get() = _loggedIn


    init {
        _loggedIn.value = null
    }

    fun logout() {
        _loggedIn.value = null
    }

    fun getplayer(name: String, password: String): LiveData<DatabasePlayer> {
        return database.playerDao.getPlayerByUserNameAndPass(name, password)
    }


    fun login(player: DatabasePlayer) {
        _loggedIn.postValue(player)
    }

    suspend fun register(username: String, password: String) {
        withContext(Dispatchers.IO) {
            var player = DatabasePlayer(
                playerId = 0,
                characterId = 11,
                coins = 500L,
                eXP = 0L,
                inventoryId = 11,
                name = username,
                password = password,
                statusPointsLeft = 5,
                statusPointsStrength = 0,
                statusPointsHitpoints = 0,
                statusPointsDefence = 0,
                statusPointsAttack = 0
            )

            database.playerDao.insertAll(player)
        }
    }

}
