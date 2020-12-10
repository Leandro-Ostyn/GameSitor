package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import be.vives.gamesitor.database.dbRelationships.ItemEffectCrossRef

@Entity
data class DatabaseItem(
    @PrimaryKey(autoGenerate = false)
    val itemId: Int,
    val name: String?,
    val image: String?,
    val cost: Long,
//    @Relation(parentColumn = "itemId", entityColumn = "itemId",entity = ItemEffectCrossRef::class)
//    val effects: List<DatabaseEffect>
)
