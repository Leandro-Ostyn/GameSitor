package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseReward
import be.vives.gamesitor.database.entities.DatabaseStage

@Dao
interface StageDao {

    @Query("select * from databasestage")
    fun getStages(): LiveData<List<DatabaseStage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg stages: DatabaseStage)
}