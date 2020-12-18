package be.vives.gamesitor.models


data class Type(
    val typeId: Int,
    val type: String?,
    val items: List<Item>
)
