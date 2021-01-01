package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseSettings

@Dao
interface SettingsDao {

    @Query("select * from settings")
    fun getSettings(): LiveData<List<DatabaseSettings>>

    @Query("Select * from settings where playerName = :playerName")
    fun getSettingsByPlayerName(playerName: String) : LiveData<DatabaseSettings>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg settings: DatabaseSettings)
}