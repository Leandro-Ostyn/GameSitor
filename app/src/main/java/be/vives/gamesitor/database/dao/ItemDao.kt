package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.vives.gamesitor.database.dbRelationships.ItemEffectCrossRef
import be.vives.gamesitor.database.entities.DatabaseItem

@Dao
interface ItemDao {

    @Query("select * from databaseitem")
     fun getItems(): LiveData<List<DatabaseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(vararg items: DatabaseItem)

    @Transaction
    @Query("Select * from databaseitem where itemId= :itemId")
     fun getItemWithEffects(itemId: Int): LiveData<List<ItemEffectCrossRef>>

}