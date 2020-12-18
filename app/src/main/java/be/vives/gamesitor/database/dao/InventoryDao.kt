package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.dbRelationships.crossRefs.InventoryItemsCrossRef
import be.vives.gamesitor.database.entities.DatabaseInventory

@Dao
interface InventoryDao {

    @Query("select * from databaseinventory")
    fun getInventories(): LiveData<List<DatabaseInventory>>

    @Query("select * from databaseinventory where inventoryId= :inventoryId ")
    fun getInventoryForPlayer(inventoryId: Int): LiveData<List<DatabaseInventory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg inventories: DatabaseInventory)

    @Query("insert into InventoryItemsCrossRef (itemId, inventoryId) values (:itemId, :inventoryId)")
    fun insertCrossReff(itemId: Int, inventoryId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCrossReffs(vararg inventories: InventoryItemsCrossRef)


}