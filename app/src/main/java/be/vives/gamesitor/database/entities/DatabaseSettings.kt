package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "settings")
data class DatabaseSettings(

    @PrimaryKey
    val settingId: Int,
    var musicOn: Boolean,
    var playerName: String,
    var passWord: String,
    var hideAnimations: Boolean,
    var setNotification: Boolean
)
