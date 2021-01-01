package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseBackground

@Dao
interface BackgroundDao {

    @Query("select * from databasebackground")
    fun getBackgrounds(): LiveData<List<DatabaseBackground>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg backgrounds: DatabaseBackground)


}