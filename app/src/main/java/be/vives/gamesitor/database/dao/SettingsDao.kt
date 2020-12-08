package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.DatabasePlayer
import be.vives.gamesitor.database.DatabaseSettings

@Dao
interface SettingsDao {

    @Query("select * from settings")
    fun getVideos(): LiveData<List<DatabaseSettings>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg player: DatabaseSettings)
}