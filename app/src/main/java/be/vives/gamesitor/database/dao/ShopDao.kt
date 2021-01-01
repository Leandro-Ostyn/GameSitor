package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.ShopItemCrossRef
import be.vives.gamesitor.database.entities.DatabaseShop

@Dao
interface ShopDao {
    @Query("select * from databaseshop")
    fun getShops(): LiveData<List<DatabaseShop>>

    @Query("select * from databaseshop where shopId= :shopId")
    fun getShop(shopId: Int): LiveData<List<DatabaseShop>>

//    @Query("select * from databaseshop where shopId= :shopId")
//    fun getShopWithItems(shopId: Int): LiveData<Shop>
//
//    @Query("select * from databaseshop")
//    fun getShopswithItems(): LiveData<List<Shop>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg shops: DatabaseShop)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReff(vararg shops: ShopItemCrossRef)
}