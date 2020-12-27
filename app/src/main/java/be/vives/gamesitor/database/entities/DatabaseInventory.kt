package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DatabaseInventory(
    @PrimaryKey(autoGenerate = true)
    val inventoryId: Int,
    val name: String
)