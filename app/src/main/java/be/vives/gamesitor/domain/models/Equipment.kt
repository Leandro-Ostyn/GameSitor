package be.vives.gamesitor.domain.models


import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import be.vives.gamesitor.database.dbRelationships.CrossRefs.EquipmentItemsCrossRef

data class Equipment(
    val equipmentId: Int,
    val name: String,
    @Relation(
        parentColumn = "equipmentId",
        entityColumn = "itemId",
        associateBy = Junction(EquipmentItemsCrossRef::class)
    )
    var Items: List<ItemWithEffect>
)
