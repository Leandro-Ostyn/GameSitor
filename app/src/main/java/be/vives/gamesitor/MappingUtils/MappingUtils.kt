package be.vives.gamesitor.MappingUtils

import be.vives.gamesitor.database.entities.DatabaseBackground
import be.vives.gamesitor.domain.models.Background

//Extensions for Background Mapping

fun List<DatabaseBackground>.asDomainModelBackground(): List<Background> {
    return map {
        Background(
            backgroundId = it.backgroundId,
            name = it.name,
            image = it.image
        )
    }
}


fun List<Background>.asDataBaseModelBackground(): List<DatabaseBackground> {

    return map {
        DatabaseBackground(
            backgroundId = it.backgroundId,
            name = it.name,
            image = it.image
        )

    }
}


////Extensions for Category Mapping
//@JvmName("asDomainModelCategory")
//suspend fun List<DatabaseCategory>.asDomainModel(types :List<TypeWithItem>): List<CategoryWithType> {
//    return map {
//        CategoryWithType(
//            databaseCategory = it,
//            types = types
//        )
//    }
//}
//
//@JvmName("asDataBaseModelCategory")
//fun List<CategoryWithType>.asDataBaseModelCategory(): List<DatabaseCategory> {
//    return map {
//        DatabaseCategory(
//            categoryId = it.databaseCategory.categoryId,
//            name = it.databaseCategory.name
//        )
//
//    }
//}
//
//
//
//@JvmName("asDomainModelDatabasePlayer")
//fun List<DatabasePlayer>.asDomainModel(): List<Player> {
//    return map {
//
//        Player(
//            playerId = it.playerId,
//            name = it.Name,
//            password = it.Password,
//            character =   Character(characterId = it.characterId,"",0, Equipment(0,"", listOf(
//                ItemWithEffect(DatabaseItem(0,"","",0L), listOf(DatabaseEffect(0,0L,"")))
//            )), Stats(0,0,0,0,0)
//            ),
//            coins = it.Coins,
//            inventory = Inventory(it.inventoryId, arrayListOf(ItemWithEffect((DatabaseItem(0,"","",0L)), listOf(DatabaseEffect(0,0L,""))))),
//            statusPointsLeft = it.statusPointsLeft,
//            statusPointsAttack = it.statusPointsAttack,
//            statusPointsDefence = it.statusPointsDefence,
//            statusPointsHitpoints = it.statusPointsHitpoints,
//            statusPointsStrength = it.statusPointsStrength,
//            EXP = it.EXP
//        )
//    }
//}
//@JvmName("asDomainModelCharacter")
//fun List<DatabaseCharacter>.asDomainModel(): List<Character> {
//    return map {
//        Character(
//            characterId = it.characterId,
//            name = it.name,
//            level = it.level,
//            equipment = it.equipment,
//            stats = it.stats
//        )
//
//    }
//}
////Extensions for Stats Mapping
//
//fun List<DatabaseStats>.asDomainModelStats(): List<Stats> {
//    return map {
//        Stats(
//            attack = it.attack,
//            statsId = it.statsId,
//            defence = it.defence,
//            lifepoints = it.lifepoints,
//            strength = it.strength
//        )
//    }
//}
//
//fun List<Stats>.asDataBaseModelStats(): List<DatabaseStats> {
//    return map {
//        DatabaseStats(
//            attack = it.attack,
//            statsId = it.statsId,
//            defence = it.defence,
//            lifepoints = it.lifepoints,
//            strength = it.strength
//        )
//
//    }
//}
//
//
//
////Extensions for Type Mapping
//@JvmName("asDomainModelType")
//fun List<DatabaseType>.asDomainModel(): List<TypeWithItem> {
//    return map {
//        Type(
//            typeId = it.typeId,
//            type = it.type,
//            items = it.items.asDomainModel()
//        )
//    }
//}
//
//@JvmName("asDomainModelType2")
//fun List<DatabaseType?>.asDomainModel(): List<TypeWithItem> {
//    return map {
//        it!!
//        Type(
//            typeId = it.typeId,
//            type = it.type,
//            items = it.items.asDomainModel()
//        )
//    }
//}
//
//@JvmName("asDataBaseModelType")
//fun List<TypeWithItem>.asDataBaseModel(): List<DatabaseType> {
//    return map {
//        DatabaseType(
//            typeId = it.typeId,
//            type = it.type,
//            items = it.items.asDataBaseModel()
//        )
//    }
//}
//
//@JvmName("asDataBaseModelType2")
//fun List<TypeWithItem?>.asDataBaseModel(): List<DatabaseType> {
//    return map {
//        it!!
//        DatabaseType(
//            typeId = it.typeId,
//            type = it.type,
//            items = it.items.asDataBaseModel()
//        )
//    }
//}
//
//
////Extensions For Item Mapping
//@JvmName("asDomainModelItem")
//fun List<DatabaseItem>.asDomainModelItem(): List<ItemWithEffect> {
//    return map {
//        Item(
//            itemId = it.item.itemId,
//            cost = it.item.cost,
//            name = it.item.name,
//            image = it.item.image,
//            //effects = it.effects.asDomainModel()
//        )
//    }
//}
//
//@JvmName("asDomainModelDatabaseItem2")
//fun List<DatabaseItem?>.asDomainModelItem(): List<ItemWithEffect> {
//    return map {
//        Item(
//            itemId = it!!.itemId,
//            cost = it.cost,
//            name = it.name,
//            image = it.image,
//         //   effects = it.
//        )
//    }
//}
//
//@JvmName("asDataBaseModelItem")
//fun List<ItemWithEffect>.asDataBaseModelItem(): List<DatabaseItem> {
//    return map {
//        DatabaseItem(
//            itemId = it.itemId,
//            cost = it.cost,
//            image = it.image,
//            name = it.name,
//          //  effects = it.effects.asDataBaseModel()
//        )
//    }
//}
//
//fun List<ItemWithEffect?>.asDataBaseModel(): List<DatabaseItem> {
//    return map {
//        DatabaseItem(
//            itemId = it!!.itemId,
//            cost = it.cost,
//            image = it.image,
//            name = it.name,
//       //     effects = it.effects.asDataBaseModel()
//        )
//    }
//}
//
////Extensions for Effect Mapping
//@JvmName("asDomainModelEffect")
//fun List<DatabaseEffect>.asDomainModel(): List<Effect> {
//    return map {
//        Effect(
//            effectId = it.effectId,
//            value = it.value,
//            attribute = it.attribute,
//          //  itemId = it.itemId
//        )
//    }
//}
//
//@JvmName("asDomainModelEffect2")
//fun List<DatabaseEffect?>.asDomainModel(): List<Effect> {
//    return map {
//
//        Effect(
//            effectId = it!!.effectId,
//            value = it.value,
//            attribute = it.attribute,
//         //   itemId = it.itemId
//        )
//
//    }
//
//}
//
//@JvmName("asDataBaseModelEffect")
//fun List<Effect>.asDataBaseModel(): List<DatabaseEffect> {
//    return map {
//        DatabaseEffect(
//            effectId = it.effectId,
//            value = it.value,
//            attribute = it.attribute,
//   //         itemId = it.itemId
//        )
//
//    }
//}
//
//@JvmName("asDataBaseModelEffect2")
//fun List<Effect?>.asDataBaseModel(): List<DatabaseEffect> {
//    return map {
//        DatabaseEffect(
//            effectId = it!!.effectId,
//            value = it.value,
//            attribute = it.attribute,
//    //        itemId = it.itemId
//        )
//
//    }
//}
//
//class Converters {
//
//
////    @TypeConverter
////    fun fromListOfEffectToString(effects: List<DatabaseEffect?>?): String? {
////        val type = object : TypeToken<List<DatabaseEffect>>() {}.type
////        return Gson().toJson(effects, type)
////    }
////
////    @TypeConverter
////    fun fromjsontoEffectlist(value: String) =
////        Gson().fromJson(value, Array<DatabaseEffect>::class.java).toList()
//
//}
