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
    fun getVideos(): LiveData<List<DatabasePlayer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg player: DatabasePlayer)

}