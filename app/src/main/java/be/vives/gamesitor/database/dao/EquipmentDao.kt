package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.EquipmentItemsCrossRef
import be.vives.gamesitor.database.entities.DatabaseEquipment

@Dao
interface EquipmentDao {

    @Query("select * from databaseequipment")
    fun getEquipments(): LiveData<List<DatabaseEquipment>>

    @Query("select * from databaseequipment where characterId= :characterId")
    fun getEquipmentFromCharacter(characterId: Int): LiveData<DatabaseEquipment>

    @Query("select * from equipmentitemscrossref where equipmentId = :equipmentId")
    fun getItemsFromEquipment(equipmentId: Int): LiveData<List<EquipmentItemsCrossRef>>


    @Query("select * from equipmentitemscrossref")
    fun getEquipmentItemsCrossRef(): LiveData<List<EquipmentItemsCrossRef>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg equipments: DatabaseEquipment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReff(vararg equipmentItems: EquipmentItemsCrossRef)

    @Query("delete from equipmentitemscrossref where itemId = :itemId and equipmentId= :equipmentId")
    fun removeCrossReff(itemId: Int, equipmentId: String)

    @Query("Update databaseequipment set characterId = :characterId where equipmentId = :equipmentId")
    fun updateCharacterIdInEquipment(characterId: String,equipmentId: String)
}