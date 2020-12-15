package be.vives.gamesitor.domain.models

import androidx.room.Embedded
import be.vives.gamesitor.domain.models.Background
import be.vives.gamesitor.domain.models.Character
import be.vives.gamesitor.domain.models.Reward

data class Stage (
    val stageId : Int,
    val name : String,
    @Embedded  val background : Background,
    @Embedded  val character : Character,
    @Embedded   val reward: Reward
)