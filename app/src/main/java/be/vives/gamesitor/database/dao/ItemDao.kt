package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.vives.gamesitor.database.dbRelationships.crossRefs.ItemEffectCrossRef
import be.vives.gamesitor.database.entities.DatabaseItem
import be.vives.gamesitor.models.Item

@Dao
interface ItemDao {

    @Query("select * from databaseitem")
    @Transaction
    fun getItems(): LiveData<List<DatabaseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg items: DatabaseItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReffEffect(vararg effectList: ItemEffectCrossRef)

    @Query("select * from itemeffectcrossref where itemId = :itemId")
    fun getEffectList(itemId: Int): LiveData<List<ItemEffectCrossRef>>

    @Query("select * from itemeffectcrossref")
    fun getEffectListSet(): LiveData<List<ItemEffectCrossRef>>

    @Transaction
    @Query("SELECT * FROM databaseitem")
    fun getAllItemsWithEffects(): LiveData<List<Item>>

    @Transaction
    @Query("SELECT * FROM databaseitem where itemId= :itemId")
    fun getItemsWithEffects(itemId: Int): LiveData<Item>

    @Transaction
    @Query("SELECT * FROM databaseitem where itemId in(:list)")
    fun getFilteredItemsWithEffects(list: List<Int>): LiveData<List<Item>>
}