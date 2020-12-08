package be.vives.gamesitor.database.entities

import androidx.room.Entity

@Entity
data class DatabaseSettings(
    val settingId: Int,
    val musicOn: Boolean,
    val playerName: String,
    val passWord:String,
    val hideAnimations: Boolean,
    val setNotification : Boolean
)
