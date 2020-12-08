package be.vives.gamesitor.network.entities

data class Equipment (
    var equipmentId :Int,
    var name : String,
    var characterId : Int,
    var Items : List<Item>
)
