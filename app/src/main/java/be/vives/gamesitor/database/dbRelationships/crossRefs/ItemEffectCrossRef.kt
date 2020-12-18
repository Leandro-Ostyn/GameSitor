package be.vives.gamesitor.database.dbRelationships.crossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["effectId","itemId"])
data class ItemEffectCrossRef(
    val effectListId: Int,
    val effectId: Int,
    val itemId: Int
)