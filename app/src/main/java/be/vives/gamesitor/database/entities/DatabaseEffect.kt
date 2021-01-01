package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseEffect(
    @PrimaryKey
    val effectId: Int,
    val value: Long,
    val attribute: String?
)


