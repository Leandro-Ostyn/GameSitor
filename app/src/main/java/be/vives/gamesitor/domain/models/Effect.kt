package be.vives.gamesitor.domain.models
data class Effect (
    val effectId:Int,
    val itemId:Int, //Testing for room
    val value: Long,
    val attribute : String?
        )