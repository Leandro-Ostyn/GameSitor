package be.vives.gamesitor.database.entities.dbRelationships.crossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EquipmentItemsCrossRef(
    @PrimaryKey(autoGenerate = true)
    val equipmentItemsId: Int,
    val equipmentId: String,
    val itemId: Int
)
