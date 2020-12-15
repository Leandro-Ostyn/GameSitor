package be.vives.gamesitor.database.entities

import androidx.room.*

@Entity
data class DatabaseItem(
    @PrimaryKey(autoGenerate = false)
    val itemId: Int,
    val name: String?,
    val image: String?,
    val cost: Long,



)
