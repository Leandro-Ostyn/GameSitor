package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.vives.gamesitor.domain.models.Character
import be.vives.gamesitor.domain.models.Equipment
import be.vives.gamesitor.domain.models.Stats

@Entity
data class DatabaseCharacter(
    @PrimaryKey
    val characterId: Int,
    val name: String,
    val level: Int,
    val exp: Long,
    val StatsId: Int,
    val IsHero: Boolean,
    val image: String

)
