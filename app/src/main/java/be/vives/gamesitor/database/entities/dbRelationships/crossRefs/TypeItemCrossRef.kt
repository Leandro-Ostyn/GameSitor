package be.vives.gamesitor.database.entities.dbRelationships.crossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TypeItemCrossRef(
    @PrimaryKey(autoGenerate = true)
    val typeItemId: Int,
    val typeId: Int,
    val itemId: Int
)
