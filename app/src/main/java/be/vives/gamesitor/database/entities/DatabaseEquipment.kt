package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseEquipment (
    @PrimaryKey
    var equipmentId :Int,
    var name : String,
    var characterId : Int,
)
