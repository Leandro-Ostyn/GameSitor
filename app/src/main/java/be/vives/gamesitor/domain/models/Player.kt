package be.vives.gamesitor.domain.models

import androidx.room.Embedded

data class Player(
    var playerId: Int,
    var name: String,
    var password: String,
    @Embedded var character: Character,
    var coins: Long,
    @Embedded var inventory: Inventory,
    val statusPointsLeft: Int,
    val statusPointsAttack: Int,
    val statusPointsDefence: Int,
    val statusPointsStrength: Int,
    val statusPointsHitpoints: Int,
    val EXP: Long
)