package be.vives.gamesitor.models

data class Player(
    var playerId: Int,
    var name: String,
    var password: String,
    var character: Character,
    var coins: Long,
    var inventory: Inventory,
    var statusPointsLeft: Int,
    var statusPointsAttack: Int,
    var statusPointsDefence: Int,
    var statusPointsStrength: Int,
    var statusPointsHitpoints: Int,
    var progress: Int
)