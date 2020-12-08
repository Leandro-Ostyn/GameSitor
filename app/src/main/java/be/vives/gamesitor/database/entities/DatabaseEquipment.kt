package be.vives.gamesitor.database.entities

import androidx.room.Entity

@Entity
data class DatabaseEquipment (
    var equipmentId :Int,
    var name : String,
    var characterId : Int,
    var Items : List<DatabaseItem>
)
