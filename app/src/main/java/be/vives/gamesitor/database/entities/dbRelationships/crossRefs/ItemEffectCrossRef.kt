package be.vives.gamesitor.database.entities.dbRelationships.crossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEffectCrossRef(
    @PrimaryKey(autoGenerate = true)
    val effectListId: Int,
    val effectId: Int,
    val itemId: Int
)