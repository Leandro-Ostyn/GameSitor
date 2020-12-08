package be.vives.gamesitor.domain.models

data class Character (
    var characterId : Int,
    var name: String,
    var level: Int,
    var equipment : Equipment,
    var stats : Stats

)