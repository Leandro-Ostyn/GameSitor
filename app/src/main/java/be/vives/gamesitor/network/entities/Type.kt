package be.vives.gamesitor.network.entities

data class Type (
    val typeId: Int,
    val type: String,
    val items: List<Item>
)