package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.vives.gamesitor.domain.models.Background

@Entity
data class DatabaseBackground(
    @PrimaryKey
    var backgroundId: Int,
    var name: String,
    var image: String

)


