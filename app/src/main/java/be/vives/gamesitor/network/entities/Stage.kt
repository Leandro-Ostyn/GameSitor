package be.vives.gamesitor.network.entities

data class Stage (
    var stageId : Int,
    var name : String,
    var background : Background,
    var enemy : Character,
    var reward: Reward
)