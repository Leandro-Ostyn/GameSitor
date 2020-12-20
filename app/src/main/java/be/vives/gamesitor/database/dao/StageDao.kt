package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseReward
import be.vives.gamesitor.database.entities.DatabaseStage
import be.vives.gamesitor.models.Stage

@Dao
interface StageDao {

    @Query("select * from databasestage")
    fun getStages(): LiveData<List<DatabaseStage>>

    @Query("select * from databasestage where stageId= :stageId")
    fun getStageById(stageId : Int): LiveData<DatabaseStage>


//    @Query("select * from databasestage where stageId= :stageId")
//    fun getStageByIdJoins(stageId : Int): LiveData<Stage>

//    @Query("select * from databasestage where stageId= :stageId")
//    fun getStageByIdWithCharacterRewardAndBackground(stageId : Int): LiveData<Stage>
//
//    @Query("select * from databasestage")
//    fun getAllStageByIdWithCharacterRewardAndBackground(): LiveData<Stage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg stages: DatabaseStage)
}