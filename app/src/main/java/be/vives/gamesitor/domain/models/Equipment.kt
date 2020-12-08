package be.vives.gamesitor.domain.models

data class Equipment (
    var equipmentId :Int,
    var name : String,
    var characterId : Int,
    var Items : List<Item>
)
