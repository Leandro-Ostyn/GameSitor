package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseStage(
    @PrimaryKey
    var stageId: Int,
    var name: String,
    var backgroundId: Int,
    var characterId: String,
    var rewardId: Int
)