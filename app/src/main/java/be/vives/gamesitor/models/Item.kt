package be.vives.gamesitor.models

import androidx.room.Junction
import androidx.room.Relation
import be.vives.gamesitor.database.dbRelationships.crossRefs.ItemEffectCrossRef
import be.vives.gamesitor.database.entities.DatabaseEffect

//@Parcelize
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

    val effects: List<DatabaseEffect>)
//) : Parcelable {
//    val itemIdP get() = itemId
//    val nameP get() = name
//    val imageP get() = image
//    val costP get() = cost
//    val effectsP get() =  effects
//}
