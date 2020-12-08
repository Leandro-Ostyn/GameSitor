package be.vives.gamesitor.database.entities

import androidx.room.Entity
import be.vives.gamesitor.domain.models.Background

@Entity
data class DatabaseBackground(
    var backgroundId: Int,
    var name: String,
    var image: String

)


fun List<DatabaseBackground>.asDomainModel(): List<Background> {
    return map {
        Background(
            backgroundId = it.backgroundId,
            name = it.name,
            image = it.image
        )
    }
}


fun List<Background>.asDataBaseModel(): Array<DatabaseBackground> {
    return map {
        DatabaseBackground(
            backgroundId = it.backgroundId,
            name = it.name,
            image = it.image
        )

    }.toTypedArray()
}