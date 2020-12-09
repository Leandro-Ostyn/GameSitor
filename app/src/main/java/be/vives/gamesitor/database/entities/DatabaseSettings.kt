package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.vives.gamesitor.domain.models.Settings

@Entity(tableName = "settings")
data class DatabaseSettings constructor(

    @PrimaryKey
    val settingId: Int,
    val musicOn: Boolean,
    val playerName: String,
    val passWord: String,
    val hideAnimations: Boolean,
    val setNotification: Boolean
)

fun List<DatabaseSettings>.asDomainModel(): List<Settings> {
    return map {
        Settings(
            settingId = it.settingId,
            musicOn = it.musicOn,
            playerName = it.playerName,
            passWord = it.passWord,
            hideAnimations = it.hideAnimations,
            setNotification = it.setNotification
        )
    }
}