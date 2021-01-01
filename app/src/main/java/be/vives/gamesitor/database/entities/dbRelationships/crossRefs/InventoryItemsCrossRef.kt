package be.vives.gamesitor.database.entities.dbRelationships.crossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InventoryItemsCrossRef(
    @PrimaryKey(autoGenerate = true)
    val inventoryItemId: Int,
    val inventoryId: String,
    val itemId: Int
)
