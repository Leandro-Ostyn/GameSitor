package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabasePlayer constructor(

    @PrimaryKey(autoGenerate = true)
    val playerId: Int,
    val name: String,
    val password: String,
    val characterId: Int,
    val coins: Long,
    val inventoryId: Int,
    val statusPointsLeft: Int,
    val statusPointsAttack: Int,
    val statusPointsDefence: Int,
    val statusPointsStrength: Int,
    val statusPointsHitpoints: Int,
    val eXP: Long
)






