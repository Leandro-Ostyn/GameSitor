package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.vives.gamesitor.models.Background
import be.vives.gamesitor.models.Effect

@Entity
data class DatabaseEffect(
    @PrimaryKey
    val effectId: Int,
    val value: Long,
    val attribute: String?
)


