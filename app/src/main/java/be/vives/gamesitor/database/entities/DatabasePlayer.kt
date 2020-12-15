package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabasePlayer constructor(

    @PrimaryKey
    val playerId: Int,
    val Name: String,
    val Password: String,
    val characterId: Int,
    val Coins: Long,
    val inventoryId: Int,
    val statusPointsLeft: Int,
    val statusPointsAttack: Int,
    val statusPointsDefence: Int,
    val statusPointsStrength: Int,
    val statusPointsHitpoints: Int,
    val EXP: Long
)






