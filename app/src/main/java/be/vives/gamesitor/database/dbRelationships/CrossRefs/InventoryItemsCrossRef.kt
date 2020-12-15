package be.vives.gamesitor.database.dbRelationships.CrossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["inventoryId", "itemId"])
data class InventoryItemsCrossRef(

    val inventoryItemId: Int,
    val inventoryId: Int,
    val itemId: Int
)
