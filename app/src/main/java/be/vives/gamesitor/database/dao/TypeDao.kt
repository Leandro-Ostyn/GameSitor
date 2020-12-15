package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.dbRelationships.CrossRefs.InventoryItemsCrossRef
import be.vives.gamesitor.database.dbRelationships.CrossRefs.TypeItemCrossRef
import be.vives.gamesitor.database.entities.DatabaseReward
import be.vives.gamesitor.database.entities.DatabaseType

@Dao
interface TypeDao {

    @Query("select * from databasetype")
    fun getTypes(): LiveData<List<DatabaseType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg types: DatabaseType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReff(vararg types: TypeItemCrossRef)

}