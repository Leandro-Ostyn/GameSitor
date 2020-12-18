package be.vives.gamesitor.models

data class Shop(
    val shopId: Int,
    val name: String,
    var shopItems: ArrayList<Item> = ArrayList()

)