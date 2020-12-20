package be.vives.gamesitor.models

import androidx.room.Embedded

data class Character(
    var characterId: Int,
    var name: String,
    var level: Int,
     var equipment: Equipment,
     var stats: Stats

)