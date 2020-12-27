package be.vives.gamesitor.models

import androidx.room.Junction
import androidx.room.Relation
import be.vives.gamesitor.database.dbRelationships.crossRefs.InventoryItemsCrossRef
import be.vives.gamesitor.database.entities.DatabaseItem


data class Inventory(

    val inventoryId: Int,
    val items: List<Item>
)