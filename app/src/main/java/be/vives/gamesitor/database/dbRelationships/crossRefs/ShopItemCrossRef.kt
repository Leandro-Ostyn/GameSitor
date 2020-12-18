package be.vives.gamesitor.database.dbRelationships.crossRefs

import androidx.room.Entity

@Entity(primaryKeys = ["shopId", "itemId"])
data class ShopItemCrossRef(
    val stockId: Int,
    val shopId: Int,
    val itemId: Int,
)
