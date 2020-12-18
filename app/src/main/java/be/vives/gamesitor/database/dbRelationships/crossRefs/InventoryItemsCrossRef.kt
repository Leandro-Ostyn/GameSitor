package be.vives.gamesitor.database.dbRelationships.crossRefs

import androidx.room.Entity

@Entity(primaryKeys = ["inventoryId", "itemId"])
data class InventoryItemsCrossRef(
    val inventoryItemId: Int,
    val inventoryId: Int,
    val itemId: Int
)
