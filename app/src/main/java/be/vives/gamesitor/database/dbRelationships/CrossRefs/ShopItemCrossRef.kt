package be.vives.gamesitor.database.dbRelationships.CrossRefs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["shopId", "itemId"])
data class ShopItemCrossRef(
    val stockId: Int,
    val shopId: Int,
    val itemId: Int,
)
