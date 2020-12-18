package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseInventory
import be.vives.gamesitor.database.entities.DatabaseReward
import be.vives.gamesitor.models.Reward

@Dao
interface RewardDao {

    @Query("select * from databasereward")
    fun getRewards(): LiveData<List<DatabaseReward>>

    @Query("select * from databasereward where rewardId= :rewardId")
    fun getReward(rewardId: Int): LiveData<DatabaseReward>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg rewards: DatabaseReward)

//    @Query("select * from databasereward")
//    fun getRewardsWithItems(): LiveData<List<Reward>>
//
//    @Query("select * from databasereward where rewardId= :rewardId")
//    fun getRewardWithItem(rewardId: Int): LiveData<Reward>

}