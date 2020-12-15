package be.vives.gamesitor.domain.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import be.vives.gamesitor.database.dbRelationships.CrossRefs.CategoryTypeCrossRef
import be.vives.gamesitor.database.entities.DatabaseCategory
import be.vives.gamesitor.database.entities.DatabaseType

data class CategoryWithType(
    @Embedded val databaseCategory: DatabaseCategory,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "typeId",
        associateBy = Junction(CategoryTypeCrossRef::class)
    )
    val types: List<TypeWithItem>
)