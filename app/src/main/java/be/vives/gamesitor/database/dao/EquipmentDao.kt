package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.dbRelationships.crossRefs.EquipmentItemsCrossRef
import be.vives.gamesitor.database.entities.DatabaseEquipment

@Dao
interface EquipmentDao {

    @Query("select * from databaseequipment")
    fun getEquipments(): LiveData<List<DatabaseEquipment>>

//    @Query("select * from databaseequipment")
//    fun getEquipmentsWithItems(): LiveData<List<Equipment>>
//
//    @Query("select * from databaseequipment where characterId= :characterId")
//    fun getEquipmentsWithItemsFromCharacter(characterId: Int): LiveData<Equipment>
//
//    @Query("Update equipmentitemscrossref set equipmentId= :equipmentId, itemId= :itemId where equipmentItemsId= :equipmentItemsId")
//    fun setEquipmentItemsFromCharacter(
//        equipmentItemsId: Int,
//        equipmentId: Int,
//        itemId: Int
//    ): LiveData<Equipment>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg equipments: DatabaseEquipment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReff(vararg equipmentItems: EquipmentItemsCrossRef)
}