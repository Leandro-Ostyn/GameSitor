package be.vives.gamesitor.network.entities

data class Player (
    var playerId: Int,
    var name: String,
    var password: String,
    var character: Character,
    var coins: Long,
    var inventory: Inventory
)