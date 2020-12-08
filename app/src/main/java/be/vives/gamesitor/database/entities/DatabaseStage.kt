package be.vives.gamesitor.database.entities

import androidx.room.Entity

@Entity
data class DatabaseStage (
    var stageId : Int,
    var name : String,
    var background : DatabaseBackground,
    var enemy : DatabaseCharacter,
    var reward: DatabaseReward
)