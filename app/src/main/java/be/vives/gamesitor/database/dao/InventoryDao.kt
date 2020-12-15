package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.dbRelationships.CrossRefs.InventoryItemsCrossRef
import be.vives.gamesitor.database.entities.DatabaseInventory

@Dao
interface InventoryDao {

    @Query("select * from databaseinventory")
    fun getEquipments(): LiveData<List<DatabaseInventory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg inventories: DatabaseInventory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReff(vararg inventories: InventoryItemsCrossRef)
}