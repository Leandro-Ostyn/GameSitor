package be.vives.gamesitor.models

data class Character(
    val characterId: String,
    val name: String,
    var exp: Long,
    val IsHero: Boolean,
    val image: String,
    val stats: Stats,
    val equipment: Equipment

)