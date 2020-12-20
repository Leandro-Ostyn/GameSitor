package be.vives.gamesitor.models


import androidx.room.Junction
import androidx.room.Relation
import be.vives.gamesitor.database.dbRelationships.crossRefs.EquipmentItemsCrossRef
import be.vives.gamesitor.database.entities.DatabaseItem

data class Equipment(
    val equipmentId: Int,
    val name: String,
    @Relation(
        parentColumn = "equipmentId",
        entityColumn = "itemId",
        associateBy = Junction(EquipmentItemsCrossRef::class)
    )
    var items: List<DatabaseItem>
)
