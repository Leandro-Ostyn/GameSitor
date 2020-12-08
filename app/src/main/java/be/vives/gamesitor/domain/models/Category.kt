package be.vives.gamesitor.domain.models

data class Category (
    val categoryId: Int,
    val name: String?,
    val types: List<Type?>)
