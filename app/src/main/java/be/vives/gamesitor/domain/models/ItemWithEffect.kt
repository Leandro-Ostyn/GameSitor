package be.vives.gamesitor.domain.models


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import be.vives.gamesitor.database.dbRelationships.CrossRefs.CategoryTypeCrossRef
import be.vives.gamesitor.database.dbRelationships.CrossRefs.ItemEffectCrossRef
import be.vives.gamesitor.database.entities.DatabaseEffect
import be.vives.gamesitor.database.entities.DatabaseItem
import be.vives.gamesitor.domain.models.Effect

data class ItemWithEffect(
    @Embedded
    val item: DatabaseItem,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "effectId",
        associateBy = Junction(ItemEffectCrossRef::class)
    )
    val effects: List<DatabaseEffect>
)
