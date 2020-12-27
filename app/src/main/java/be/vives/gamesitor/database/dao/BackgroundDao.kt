package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.vives.gamesitor.database.entities.DatabaseBackground
import kotlinx.coroutines.selects.select

@Dao
interface BackgroundDao {

    @Query("select * from databasebackground")
    fun getBackgrounds(): LiveData<List<DatabaseBackground>>

    @Query("select * from databasebackground where backgroundId = :backgroundId")
    fun getBackgroundById(backgroundId: Int): LiveData<DatabaseBackground>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg backgrounds: DatabaseBackground)


}