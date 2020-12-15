package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.dbRelationships.CrossRefs.CategoryTypeCrossRef
import be.vives.gamesitor.database.dbRelationships.CrossRefs.ItemEffectCrossRef
import be.vives.gamesitor.database.entities.DatabaseCategory
import be.vives.gamesitor.database.entities.DatabaseEffect

@Dao
interface EffectDao {

    @Query("select * from databaseeffect")
    fun getEffects(): LiveData<List<DatabaseEffect>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg effects: DatabaseEffect)


}