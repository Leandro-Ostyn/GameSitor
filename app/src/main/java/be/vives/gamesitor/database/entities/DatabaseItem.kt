package be.vives.gamesitor.database.entities

import androidx.room.Entity


@Entity
data class DatabaseItem (
 val itemId :Int,
    val name : String?,
    val image : String?,
    val cost : Long,
    val effects : List<DatabaseEffect?>
)
