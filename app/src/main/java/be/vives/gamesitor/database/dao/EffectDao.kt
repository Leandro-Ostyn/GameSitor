package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseEffect

@Dao
interface EffectDao {

    @Query("select * from databaseeffect")
    fun getEffects(): LiveData<List<DatabaseEffect>>

    @Query("select * from databaseeffect where effectId = :effectId")
    fun getEffect(effectId: Int): LiveData<DatabaseEffect>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg effects: DatabaseEffect)
}