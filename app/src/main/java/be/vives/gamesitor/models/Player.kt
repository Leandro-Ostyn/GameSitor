package be.vives.gamesitor.models

import androidx.room.Embedded

data class Player(
    var playerId: Int,
    var name: String,
    var password: String,
    var character: Character,
    var coins: Long,
    var inventory: Inventory,
    val statusPointsLeft: Int,
    val statusPointsAttack: Int,
    val statusPointsDefence: Int,
    val statusPointsStrength: Int,
    val statusPointsHitpoints: Int,
    var EXP: Long
)