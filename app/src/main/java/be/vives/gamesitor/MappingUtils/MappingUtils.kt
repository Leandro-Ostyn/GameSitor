package be.vives.gamesitor.MappingUtils

import be.vives.gamesitor.database.entities.*
import be.vives.gamesitor.domain.models.*

//Extensions for Background Mapping


fun List<DatabaseBackground>.asDomainModel(): List<Background> {
    return map {
        Background(
            backgroundId = it.backgroundId,
            name = it.name,
            image = it.image
        )
    }
}
@JvmName("asDataBaseModelBackground")
fun List<Background>.asDataBaseModel(): List<DatabaseBackground> {
    return map {
        DatabaseBackground(
            backgroundId = it.backgroundId,
            name = it.name,
            image = it.image
        )

    }
}

//Extensions for Stats Mapping

@JvmName("asDomainModelStats")
fun List<DatabaseStats>.asDomainModel(): List<Stats> {
    return map {
        Stats(
            attack = it.attack,
            statsId = it.statsId,
            defence = it.defence,
            lifepoints = it.lifepoints,
            strength = it.strength
        )
    }
}
@JvmName("asDataBaseModelStats")
fun List<Stats>.asDataBaseModel(): List<DatabaseStats> {
    return map {
        DatabaseStats(
            attack = it.attack,
            statsId = it.statsId,
            defence = it.defence,
            lifepoints = it.lifepoints,
            strength = it.strength
        )

    }
}


//Extensions for Category Mapping
@JvmName("asDomainModelCategory")
fun List<DatabaseCategory>.asDomainModel(): List<Category> {
    return map {
        Category(
            categoryId = it.categoryId,
            name = it.name,
            types = it.types.asDomainModel()
        )
    }
}
@JvmName("asDataBaseModelCategory")
fun List<Category>.asDataBaseModel(): List<DatabaseCategory> {
    return map {
        DatabaseCategory(
            categoryId = it.categoryId,
            types = it.types.asDataBaseModel(),
            name = it.name
        )

    }
}


//Extensions for Type Mapping
@JvmName("asDomainModelType")
fun List<DatabaseType>.asDomainModel(): List<Type> {
    return map {
        Type(
            typeId = it.typeId,
            type = it.type,
            items = it.items.asDomainModel()
        )
    }
}
@JvmName("asDomainModelType2")
fun List<DatabaseType?>.asDomainModel(): List<Type> {
    return map {
        it!!
        Type(
            typeId = it.typeId,
            type = it.type,
            items = it.items.asDomainModel()
        )
    }
}
@JvmName("asDataBaseModelType")
fun List<Type>.asDataBaseModel(): List<DatabaseType> {
    return map {
        DatabaseType(
            typeId = it.typeId,
            type = it.type,
            items = it.items.asDataBaseModel()
        )
    }
}
@JvmName("asDataBaseModelType2")
fun List<Type?>.asDataBaseModel(): List<DatabaseType> {
    return map {
        it!!
        DatabaseType(
            typeId = it.typeId,
            type = it.type,
            items = it.items.asDataBaseModel()
        )
    }
}


//Extensions For Item Mapping
@JvmName("asDomainModelItem")
fun List<DatabaseItem>.asDomainModel(): List<Item> {
    return map {
        Item(
            itemId = it.itemId,
            cost = it.cost,
            name = it.name,
            image = it.image,
        //    effects = it.effects.asDomainModel()
        )
    }
}
@JvmName("asDomainModelDatabaseItem2")
fun List<DatabaseItem?>.asDomainModel(): List<Item> {
    return map {
        Item(
            itemId = it!!.itemId,
            cost = it.cost,
            name = it.name,
            image = it.image,
     //       effects = it.effects.asDomainModel()
        )
    }
}
@JvmName("asDataBaseModelItem")
fun List<Item>.asDataBaseModel(): List<DatabaseItem> {
    return map {
        DatabaseItem(
            itemId = it.itemId,
            cost = it.cost,
            image = it.image,
            name = it.name,
    //        effects = it.effects.asDataBaseModel()
        )
    }
}
fun List<Item?>.asDataBaseModel(): List<DatabaseItem> {
    return map {
        DatabaseItem(
            itemId = it!!.itemId,
            cost = it.cost,
            image = it.image,
            name = it.name,
       //     effects = it.effects.asDataBaseModel()
        )
    }
}

//Extensions for Effect Mapping
@JvmName("asDomainModelEffect")
fun List<DatabaseEffect>.asDomainModel(): List<Effect> {
    return map {
        Effect(
            effectId = it.effectId,
            value = it.value,
            attribute = it.attribute,
            itemId = it.itemId
        )
    }
}
@JvmName("asDomainModelEffect2")
fun List<DatabaseEffect?>.asDomainModel(): List<Effect> {
    return map {

        Effect(
            effectId = it!!.effectId,
            value = it.value,
            attribute = it.attribute,
                    itemId = it.itemId
        )

    }

}
@JvmName("asDataBaseModelEffect")
fun List<Effect>.asDataBaseModel(): List<DatabaseEffect> {
    return map {
        DatabaseEffect(
            effectId = it.effectId,
            value = it.value,
            attribute = it.attribute,
            itemId = it.itemId
        )

    }
}
@JvmName("asDataBaseModelEffect2")
fun List<Effect?>.asDataBaseModel(): List<DatabaseEffect> {
    return map {
        DatabaseEffect(
            effectId = it!!.effectId,
            value = it.value,
            attribute = it.attribute,
            itemId = it.itemId
        )

    }
}
