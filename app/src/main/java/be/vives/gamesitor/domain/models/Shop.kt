package be.vives.gamesitor.domain.models

import androidx.room.Junction
import androidx.room.Relation
import be.vives.gamesitor.database.dbRelationships.CrossRefs.ShopItemCrossRef

data class Shop(
    val shopId: Int,
    val name: String,

    @Relation(
        parentColumn = "shopId",
        entityColumn = "itemId",
        associateBy = Junction(ShopItemCrossRef::class)
    )
    var shopItems: ArrayList<ItemWithEffect> = ArrayList()

)