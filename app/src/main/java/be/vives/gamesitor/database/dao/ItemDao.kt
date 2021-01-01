package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.ItemEffectCrossRef
import be.vives.gamesitor.database.entities.DatabaseItem
import be.vives.gamesitor.models.Item

@Dao
interface ItemDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg items: DatabaseItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReffEffect(vararg effectList: ItemEffectCrossRef)

    @Transaction
    @Query("SELECT * FROM databaseitem")
    fun getAllItemsWithEffects(): LiveData<List<Item>>

    @Transaction
    @Query("SELECT * FROM databaseitem where itemId= :itemId")
    fun getItemsWithEffects(itemId: Int): LiveData<Item>
}