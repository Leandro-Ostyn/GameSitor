package be.vives.gamesitor.database.dbRelationships.CrossRefs

import androidx.room.Entity

@Entity(primaryKeys = ["itemId", "effectId"])
data class ItemEffectCrossRef(
    val effectListId: Int,
    val effectId: Int,
    val itemId: Int
)