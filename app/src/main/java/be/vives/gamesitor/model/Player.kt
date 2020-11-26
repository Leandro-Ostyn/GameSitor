package be.vives.gamesitor.model

class Player {
    var playerId: Int = 0
    var name: String = ""
    var password: String = ""
    var character: Character = Character()
    var coins: Long = 0L
    var inventory: Inventory = Inventory()
}