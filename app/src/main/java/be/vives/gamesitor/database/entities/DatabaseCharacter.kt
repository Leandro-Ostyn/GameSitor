package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseCharacter(
    @PrimaryKey
    val characterId: String,
    val name: String,
    val exp: Long,
    val isHero: Boolean,
    val image: String,
    val statsId: String,
    val equipmentId: String
)
