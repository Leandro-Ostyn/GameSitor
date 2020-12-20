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


    @Query("Select * from DatabasePlayer where name =:name and password = :password")
     fun getPlayerByUserNameAndPass(name: String, password: String): LiveData<DatabasePlayer>

//    @Query("Select * from DatabasePlayer where playerId =:playerId")
//    fun getPlayerByUserNameWithCharacterAndInventory(playerId: Int): LiveData<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg player: DatabasePlayer)

    @Query("Insert into DatabasePlayer (name,password) values (:name, :password) ")
    fun insertplayer(name: String, password: String)

}