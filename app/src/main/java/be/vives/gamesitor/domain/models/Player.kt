package be.vives.gamesitor.domain.models
data class Player (
    var playerId: Int,
    var name: String,
    var password: String,
    var character: Character,
    var coins: Long,
    var inventory: Inventory,
    val statusPointsLeft : Int,
    val statusPointsAttack: Int,
    val statusPointsDefence: Int,
    val statusPointsStrength : Int,
    val statusPointsHitpoints : Int,
    val EXP: Long
)