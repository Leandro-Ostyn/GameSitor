package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.vives.gamesitor.domain.models.Background
import be.vives.gamesitor.domain.models.Category

@Entity
data class DatabaseCategory constructor (
    @PrimaryKey
    val categoryId: Int,
    val name: String?,
    val types: List<DatabaseType?>)

