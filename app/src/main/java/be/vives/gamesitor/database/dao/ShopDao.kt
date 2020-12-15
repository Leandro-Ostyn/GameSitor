package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.dbRelationships.CrossRefs.InventoryItemsCrossRef
import be.vives.gamesitor.database.dbRelationships.CrossRefs.ShopItemCrossRef
import be.vives.gamesitor.database.entities.DatabaseReward
import be.vives.gamesitor.database.entities.DatabaseShop

@Dao
interface ShopDao {
    @Query("select * from databaseshop")
    fun getShops(): LiveData<List<DatabaseShop>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg shops: DatabaseShop)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReff(vararg shops: ShopItemCrossRef)
}