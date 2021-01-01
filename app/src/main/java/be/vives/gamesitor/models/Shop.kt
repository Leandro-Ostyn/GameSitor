package be.vives.gamesitor.models

//If there is ever need in switching in shop display this class can be implemented
data class Shop(
    val shopId: Int,
    val name: String,
    var shopItems: MutableList<Item>

)