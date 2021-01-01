package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.InventoryItemsCrossRef
import be.vives.gamesitor.database.entities.DatabaseInventory

@Dao
interface InventoryDao {

    @Query("select * from databaseinventory")
    fun getInventories(): LiveData<List<DatabaseInventory>>

    @Query("select * from databaseinventory where inventoryId= :inventoryId ")
    fun getInventoryForPlayer(inventoryId: String): LiveData<DatabaseInventory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg inventories: DatabaseInventory): List<Long>

    @Query("insert into InventoryItemsCrossRef (itemId, inventoryId) values (:itemId, :inventoryId)")
    fun insertCrossReff(itemId: Int, inventoryId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCrossReffs(vararg inventories: InventoryItemsCrossRef)

    @Transaction
    @Query("SELECT * FROM InventoryItemsCrossRef where inventoryId = :inventoryId")
    fun getCrossReffInventoryItems(inventoryId: String): LiveData<List<InventoryItemsCrossRef>>

    @Query("SELECT * FROM InventoryItemsCrossRef")
    fun getAllCrossReffInventoryItems(): LiveData<List<InventoryItemsCrossRef>>


    @Query("Delete  FROM InventoryItemsCrossRef where inventoryId = :inventoryId and itemId =:itemId")
   fun deleteCrossReff(inventoryId: String,itemId: Int)
}