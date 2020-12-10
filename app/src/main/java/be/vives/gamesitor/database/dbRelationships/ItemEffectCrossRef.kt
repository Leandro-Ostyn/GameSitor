package be.vives.gamesitor.database.dbRelationships


import androidx.room.Embedded
import androidx.room.Relation
import be.vives.gamesitor.database.entities.DatabaseEffect
import be.vives.gamesitor.database.entities.DatabaseItem
import be.vives.gamesitor.domain.models.Effect

data class ItemEffectCrossRef(
    @Embedded val item: DatabaseItem,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "itemId"
    )
    val effects: List<DatabaseEffect>
    )
