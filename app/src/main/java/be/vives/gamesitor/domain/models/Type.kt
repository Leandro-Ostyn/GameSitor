package be.vives.gamesitor.domain.models

data class Type (
    val typeId: Int,
    val type: String?,
    val items: List<Item?>
)