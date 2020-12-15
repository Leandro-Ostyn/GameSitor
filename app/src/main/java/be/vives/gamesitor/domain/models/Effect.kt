package be.vives.gamesitor.domain.models
data class Effect (
    val effectId:Int,
    val value: Long,
    val attribute : String?
        )