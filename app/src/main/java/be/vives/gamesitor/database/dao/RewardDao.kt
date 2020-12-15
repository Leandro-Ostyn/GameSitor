package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseInventory
import be.vives.gamesitor.database.entities.DatabaseReward

@Dao
interface RewardDao {

    @Query("select * from databasereward")
    fun getRewards(): LiveData<List<DatabaseReward>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg rewards: DatabaseReward)

}