package be.vives.gamesitor.database.dbRelationships.CrossRefs

import androidx.room.Entity

@Entity(primaryKeys = ["typeId", "itemId"])
data class TypeItemCrossRef(
    val typeItemId: Int,
    val typeId: Int,
    val itemId: Int
)
