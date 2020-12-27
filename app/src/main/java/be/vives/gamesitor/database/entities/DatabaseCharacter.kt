package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseCharacter(
    @PrimaryKey
    val characterId: Int,
    val name: String,
    val exp: Long,
    val isHero: Boolean,
    val image: String,
    val statsId: Int,
    val equipmentId: Int
)
