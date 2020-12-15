package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseShop(
    @PrimaryKey
    val shopId: Int,
    val name: String
)