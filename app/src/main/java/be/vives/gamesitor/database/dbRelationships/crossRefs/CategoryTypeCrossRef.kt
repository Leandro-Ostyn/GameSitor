package be.vives.gamesitor.database.dbRelationships.crossRefs

import androidx.room.Entity

//IS only needed if mapping directly from Db
@Entity(primaryKeys = ["categoryId", "typeId"])
data class CategoryTypeCrossRef(
    val categoryTypeId: Int,
    val categoryId: Int,
    val typeId: Int
)