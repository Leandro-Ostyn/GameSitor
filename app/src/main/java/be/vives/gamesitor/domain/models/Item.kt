package be.vives.gamesitor.domain.models



data class Item (
 val itemId :Int,
    val name : String?,
    val image : String?,
    val cost : Long,
 //   val effects : List<Effect?>
)
