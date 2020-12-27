package be.vives.gamesitor.models

data class Equipment(
    val equipmentId: Int,
    val name: String,
    val characterId : Int,
    var items: List<Item>
)
