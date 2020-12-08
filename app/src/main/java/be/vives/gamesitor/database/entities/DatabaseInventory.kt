package be.vives.gamesitor.database.entities

import androidx.room.Entity


@Entity
data class DatabaseInventory (

    val inventoryId :Int,
    val Items :ArrayList<DatabaseItem>
)