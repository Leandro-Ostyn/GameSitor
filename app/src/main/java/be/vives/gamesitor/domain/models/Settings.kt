package be.vives.gamesitor.domain.models

data class Settings(
    val settingId: Int,
    val musicOn: Boolean,
    val playerName: String,
    val passWord:String,
    val hideAnimations: Boolean,
    val setNotification : Boolean
)
