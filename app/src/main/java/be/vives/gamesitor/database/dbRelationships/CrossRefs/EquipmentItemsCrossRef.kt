package be.vives.gamesitor.database.dbRelationships.CrossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (primaryKeys = ["equipmentId","itemId"])
data class EquipmentItemsCrossRef(
    val equipmentItemsId: Int,
    val equipmentId: Int,
    val itemId: Int
)
