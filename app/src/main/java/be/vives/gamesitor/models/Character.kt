package be.vives.gamesitor.models

import androidx.room.Embedded

data class Character(
    val characterId: Int,
    val name: String?,
    val exp: Long,
    val IsHero: Boolean,
    val image: String?,
    val stats: Stats,
    val equipment: Equipment

)