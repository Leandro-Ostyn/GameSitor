package be.vives.gamesitor.database.entities.dbRelationships.crossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

//IS only needed if mapping directly from Db
@Entity
data class CategoryTypeCrossRef(
    @PrimaryKey(autoGenerate = true)
    val categoryTypeId: Int,
    val categoryId: Int,
    val typeId: Int
)