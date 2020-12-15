package be.vives.gamesitor.domain.models

import androidx.room.Embedded

data class Character (
    var characterId : Int,
    var name: String,
    var level: Int,
    @Embedded var equipment : Equipment,
  @Embedded  var stats : Stats

)