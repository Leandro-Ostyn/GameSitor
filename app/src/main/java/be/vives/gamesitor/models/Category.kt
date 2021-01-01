package be.vives.gamesitor.models

//This class is not used yet ! this is if there ever is time to add consumables
data class Category(
    val categoryId: Int,
    val name: String?,
    val types: MutableList<Type>
)