package be.vives.gamesitor.domain.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import be.vives.gamesitor.database.dbRelationships.CrossRefs.TypeItemCrossRef
import be.vives.gamesitor.database.entities.DatabaseItem
import be.vives.gamesitor.database.entities.DatabaseType


data class TypeWithItem(
    val typeItemId:Int,
    @Embedded
    val type : DatabaseType,    @Relation(
        parentColumn = "typeId",
        entityColumn = "itemId",
        associateBy = Junction(TypeItemCrossRef::class)
    )
    val Items: List<DatabaseItem>
    )
