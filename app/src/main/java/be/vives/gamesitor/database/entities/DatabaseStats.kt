package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseStats(
@PrimaryKey
    var statsId: String,
    var lifepoints: Int,
    var attack: Int,
    var strength: Int,
    var defence: Int,
)
