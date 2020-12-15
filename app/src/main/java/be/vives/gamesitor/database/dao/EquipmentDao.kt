package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.dbRelationships.CrossRefs.EquipmentItemsCrossRef
import be.vives.gamesitor.database.dbRelationships.CrossRefs.ItemEffectCrossRef
import be.vives.gamesitor.database.entities.DatabaseCategory
import be.vives.gamesitor.database.entities.DatabaseEquipment

@Dao
interface EquipmentDao {

    @Query("select * from databaseequipment")
    fun getEquipments(): LiveData<List<DatabaseEquipment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg equipments: DatabaseEquipment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReff(vararg equipmentItems : EquipmentItemsCrossRef)
}