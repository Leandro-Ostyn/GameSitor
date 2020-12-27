package be.vives.gamesitor.models

data class Stage(
    val stageId: Int,
    val name: String,
    val background: Background,
    val character: Character,
    val reward: Reward
)