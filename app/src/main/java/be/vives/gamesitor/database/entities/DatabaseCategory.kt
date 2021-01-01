package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseCategory(
    @PrimaryKey
    val categoryId: Int,
    val name: String?,
   )

