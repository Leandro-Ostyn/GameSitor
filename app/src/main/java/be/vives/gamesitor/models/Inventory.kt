package be.vives.gamesitor.models

import androidx.room.Junction
import androidx.room.Relation
import be.vives.gamesitor.database.dbRelationships.crossRefs.InventoryItemsCrossRef


data class Inventory(

    val inventoryId: Int,
    @Relation(
        parentColumn = "inventoryId",
        entityColumn = "ItemId",
        associateBy = Junction(InventoryItemsCrossRef::class)
    )
    val items: List<Item>
)