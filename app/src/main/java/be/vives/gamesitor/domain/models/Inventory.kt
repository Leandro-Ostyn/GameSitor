package be.vives.gamesitor.domain.models

import androidx.room.Relation


data class Inventory (

    val inventoryId :Int,
    @Relation(
        parentColumn = "inventoryId",
        entityColumn = "itemId"
    )
    val Items :ArrayList<ItemWithEffect>
)