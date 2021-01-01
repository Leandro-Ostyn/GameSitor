package be.vives.gamesitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.vives.gamesitor.database.SitorDatabase
import be.vives.gamesitor.database.entities.DatabaseInventory
import be.vives.gamesitor.database.entities.DatabasePlayer
import be.vives.gamesitor.database.entities.DatabaseStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class LoginRepository(private val database: SitorDatabase) {

    private val _loggedIn = MutableLiveData<DatabasePlayer>()
    val loggedIn: LiveData<DatabasePlayer> get() = _loggedIn


    init {
        _loggedIn.value = null
    }

    fun logout() {
        _loggedIn.value = null
    }

    fun login(player: DatabasePlayer) {
        _loggedIn.postValue(player)
    }

    suspend fun getInventories(): LiveData<List<DatabaseInventory>> {
        return withContext(Dispatchers.IO) {
            return@withContext database.inventoryDao.getInventories()
        }
    }

    suspend fun createInventory(): String {
        return withContext(Dispatchers.IO) {
       val inventoryId=     UUID.randomUUID().toString()
            database.inventoryDao.insertAll(
                DatabaseInventory(
                    inventoryId = inventoryId,
                    name = "PlayerInventory"
                )
            )
            return@withContext inventoryId
        }

    }

    suspend fun register(username: String, password: String, inventoryId: String) {
        withContext(Dispatchers.IO) {
            val player = DatabasePlayer(
                playerId = 0,
                characterId = "0",
                coins = 500L,
                progress = 0,
                inventoryId = inventoryId,
                name = username,
                password = password,
                statusPointsLeft = 5,
                statusPointsStrength = 0,
                statusPointsHitpoints = 0,
                statusPointsDefence = 0,
                statusPointsAttack = 0
            )
            database.playerDao.insertAll(player)
            _loggedIn.postValue(player)
        }
    }

}
