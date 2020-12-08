package be.vives.gamesitor.network.entities

data class Category (
    val categoryId: Int,
    val name: String,
    val types: List<Type>)
