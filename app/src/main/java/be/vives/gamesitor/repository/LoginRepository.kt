package be.vives.gamesitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.vives.gamesitor.database.GameSitorDatabase
import be.vives.gamesitor.database.entities.DatabaseInventory
import be.vives.gamesitor.database.entities.DatabasePlayer
import be.vives.gamesitor.models.Inventory
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

    fun login(player: DatabasePlayer) {
        _loggedIn.postValue(player)
    }

    suspend fun getInventories(): LiveData<List<DatabaseInventory>> {
        return withContext(Dispatchers.IO) {
            return@withContext database.inventoryDao.getInventories()
        }
    }

    suspend fun createInventory(): List<Long> {
        return withContext(Dispatchers.IO) {
            return@withContext database.inventoryDao.insertAll(
                DatabaseInventory(
                    inventoryId = 0,
                    name = "PlayerInventory"
                )
            )
        }

    }

    suspend fun register(username: String, password: String, inventoryId: Int) {
        withContext(Dispatchers.IO) {
            val player = DatabasePlayer(
                playerId = 0,
                characterId = 11,
                coins = 500L,
                eXP = 0L,
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
