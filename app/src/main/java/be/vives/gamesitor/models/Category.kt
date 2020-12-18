package be.vives.gamesitor.models

data class Category(
    val categoryId: Int,
    val name: String?,
    val types: List<Type>
)