package be.vives.gamesitor.database.entities

import androidx.room.Entity

@Entity
data class DatabaseType (
    val typeId: Int,
    val type: String?,
    val items: List<DatabaseItem?>
)