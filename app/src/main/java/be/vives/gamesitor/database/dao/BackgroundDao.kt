package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseBackground
import kotlinx.coroutines.selects.select

@Dao
interface BackgroundDao {

    @Query("select * from DatabaseBackground" )
    fun getBackgrounds(): LiveData<List<DatabaseBackground>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseBackground)
}