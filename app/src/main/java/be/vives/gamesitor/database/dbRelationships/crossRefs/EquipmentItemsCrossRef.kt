package be.vives.gamesitor.database.dbRelationships.crossRefs

import androidx.room.Entity

@Entity (primaryKeys = ["equipmentId","itemId"])
data class EquipmentItemsCrossRef(
    val equipmentItemsId: Int,
    val equipmentId: Int,
    val itemId: Int
)
