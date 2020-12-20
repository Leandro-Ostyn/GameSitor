package be.vives.gamesitor.database.dbRelationships.crossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InventoryItemsCrossRef(
    @PrimaryKey(autoGenerate = true)
    val inventoryItemId: Int,
    val inventoryId: Int,
    val itemId: Int
)
