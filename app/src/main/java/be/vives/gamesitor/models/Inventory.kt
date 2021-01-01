package be.vives.gamesitor.models


data class Inventory(

    val inventoryId: String,
    val items: MutableList<Item>
)