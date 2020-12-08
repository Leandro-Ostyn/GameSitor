package be.vives.gamesitor.database.entities

import androidx.room.Entity

@Entity
data class DatabasePlayer (
    var playerId: Int,
    var name: String,
    var password: String,
    var character: DatabaseCharacter,
    var coins: Long,
    var inventory: DatabaseInventory,
    val statusPointsLeft : Int,
    val statusPointsAttack: Int,
    val statusPointsDefence: Int,
    val statusPointsStrength : Int,
    val statusPointsHitpoints : Int,
    val EXP: Long
)