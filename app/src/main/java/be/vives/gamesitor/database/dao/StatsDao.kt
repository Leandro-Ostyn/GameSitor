package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseBackground
import be.vives.gamesitor.database.entities.DatabaseStats

@Dao
interface StatsDao {

    @Query("select * from databasestats" )
    fun getStatsSet(): LiveData<List<DatabaseStats>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg stats: DatabaseStats)
}