package be.vives.gamesitor.network.entities



data class Item (
 val itemId :Int,
    val name : String,
    val image : Int,
    val cost : Long,
    val effects : List<Effect>
)
