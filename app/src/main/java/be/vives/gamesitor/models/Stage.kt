package be.vives.gamesitor.models

import androidx.room.Embedded
import be.vives.gamesitor.database.entities.DatabaseBackground
import be.vives.gamesitor.database.entities.DatabaseReward
import be.vives.gamesitor.models.Background
import be.vives.gamesitor.models.Character
import be.vives.gamesitor.models.Reward

data class Stage(
    val stageId: Int,
    val name: String,
    val background: Background,
    val character: Character,
    val reward: Reward
)