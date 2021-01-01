package be.vives.gamesitor.models

import androidx.room.Junction
import androidx.room.Relation
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.ItemEffectCrossRef
import be.vives.gamesitor.database.entities.DatabaseEffect


data class Item(

    val itemId: Int,
    val name: String?,
    val image: String?,
    val cost: Long,

    @Relation(
        parentColumn = "itemId",
        entityColumn = "effectId",
        associateBy = Junction(ItemEffectCrossRef::class)
    )

    val effects: MutableList<DatabaseEffect>)
