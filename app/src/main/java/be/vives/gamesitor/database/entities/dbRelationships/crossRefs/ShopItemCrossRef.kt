package be.vives.gamesitor.database.entities.dbRelationships.crossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShopItemCrossRef(
    @PrimaryKey(autoGenerate = true)
    val stockId: Int,
    val shopId: Int,
    val itemId: Int,
)
