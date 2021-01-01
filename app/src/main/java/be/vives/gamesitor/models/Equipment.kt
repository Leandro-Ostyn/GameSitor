package be.vives.gamesitor.models

data class Equipment(
    val equipmentId: String,
    val name: String,
    val characterId : String,
    var items: MutableList<Item>
)
