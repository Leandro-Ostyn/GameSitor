package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabasePlayer

@Dao
interface PlayerDao {
    @Query("select * from DatabasePlayer")
    fun getPlayers(): LiveData<List<DatabasePlayer>>


    @Query("Select * from DatabasePlayer where name =:name")
    fun getPlayerByUserName(name: String): LiveData<DatabasePlayer>

//    @Query("Select * from DatabasePlayer where playerId =:playerId")
//    fun getPlayerByUserNameWithCharacterAndInventory(playerId: Int): LiveData<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg player: DatabasePlayer)

    @Query("Insert into DatabasePlayer (name,password) values (:name, :password) ")
    fun insertplayer(name: String, password: String)

    @Query("Update databaseplayer set coins = :coins where playerId = :playerId ")
    fun updatePlayerCash(coins: Long, playerId: Int)

    @Query("update databaseplayer set name = :name, password = :password where playerId = :playerId")
    fun updatePlayerNameAndPass(name: String, password: String, playerId: Int)

    @Query("Update databaseplayer set coins = :coins  where playerId = :playerId ")
    fun updateCoins( coins: Long, playerId: Int)


    @Query("Update databaseplayer set characterId = :characterId where playerId = :playerId ")
    fun updatePlayerCharacter(characterId : String, playerId: Int)

    @Query("UPDATE databaseplayer set progress = :progress where playerId = :playerId")
    fun updateProgress(progress : Int, playerId: Int)

    @Query("UPDATE databaseplayer set statusPointsDefence = :statusPointsDefence, statusPointsLeft = :statusPointsLeft ,statusPointsAttack = :statusPointsAttack, statusPointsStrength = :statusPointsStrength, statusPointsHitpoints= :statusPointsHitPoints where playerId = :playerId")
    fun updateStatus(statusPointsLeft : Int, statusPointsDefence : Int ,statusPointsAttack:Int,statusPointsStrength : Int,statusPointsHitPoints : Int,playerId: Int)


}
