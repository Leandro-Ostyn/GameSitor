package be.vives.gamesitor.mappingUtils

import androidx.lifecycle.Observer
import be.vives.gamesitor.database.GameSitorDatabase
import be.vives.gamesitor.database.dbRelationships.crossRefs.ItemEffectCrossRef
import be.vives.gamesitor.database.entities.*
import be.vives.gamesitor.models.*

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


//Extensions for Category Mapping
@JvmName("asDomainModelCategory")
fun List<DatabaseCategory>.asDomainModel(types: List<Type>): List<Category> {
    return map {
        Category(
            categoryId = it.categoryId,
            name = it.name,
            types = types
        )
    }
}

@JvmName("asDataBaseModelCategory")
fun List<Category>.asDataBaseModelCategory(): List<DatabaseCategory> {
    return map {
        DatabaseCategory(
            categoryId = it.categoryId,
            name = it.name,

            )

    }
}


@JvmName("asDomainModelDatabasePlayer")
fun List<DatabasePlayer>.asDomainModel(character: Character, inventory: Inventory): List<Player> {
    return map {

        Player(
            playerId = it.playerId,
            name = it.name,
            password = it.password,
            character = character,
            coins = it.coins,
            inventory = inventory,
            statusPointsLeft = it.statusPointsLeft,
            statusPointsAttack = it.statusPointsAttack,
            statusPointsDefence = it.statusPointsDefence,
            statusPointsHitpoints = it.statusPointsHitpoints,
            statusPointsStrength = it.statusPointsStrength,
            EXP = it.eXP
        )
    }
}

@JvmName("asDomainModelCharacter")
fun List<DatabaseCharacter>.asDomainModel(equipment: Equipment, stats: Stats): List<Character> {
    return map {
        Character(
            characterId = it.characterId,
            name = it.name,
            level = it.level,
            equipment = equipment,
            stats = stats
        )

    }
}
////Extensions for Stats Mapping

fun List<DatabaseStats>.asDomainModelStats(): List<Stats> {
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

fun List<Stats>.asDataBaseModelStats(): List<DatabaseStats> {
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


//Extensions for Type Mapping
@JvmName("asDomainModelType")
fun List<DatabaseType>.asDomainModel(items: List<Item>): List<Type> {
    return map {
        Type(
            typeId = it.typeId,
            type = it.type,
            items = items
        )
    }
}

@JvmName("asDomainModelType2")
fun List<DatabaseType?>.asDomainModel(items: List<Item>): List<Type> {
    return map {
        it!!
        Type(
            typeId = it.typeId,
            type = it.type,
            items = items
        )
    }
}

@JvmName("asDataBaseModelType")
fun List<Type>.asDataBaseModel(): List<DatabaseType> {
    return map {
        DatabaseType(
            typeId = it.typeId,
            type = it.type,
        )
    }
}

@JvmName("asDataBaseModelType2")
fun List<Type?>.asDataBaseModel(): List<DatabaseType> {
    return map {
        it!!
        DatabaseType(
            typeId = it.typeId,
            type = it.type
        )
    }
}


////Extensions For Item Mapping
@JvmName("asDomainModelItem")
fun List<DatabaseItem>.asDomainModelItem(database: GameSitorDatabase): List<Item> {
    var itemlist = mutableListOf<Item>()
    for (dbitem in this) {

        var effectlist = mutableListOf<DatabaseEffect>()

      database.itemDao.getEffectList(dbitem.itemId).observeForever(Observer { list ->
            for (crosReff in list) {
                database.effectDao.getEffect(crosReff.effectId)
                    .observeForever(Observer {
                        effectlist.add(it)
                    })

            }
        })

        var item = Item(
            itemId = dbitem.itemId,
            cost = dbitem.cost,
            image = dbitem.image,
            name = dbitem.name,
            effects = effectlist
        )

        itemlist.add(item)
    }
    return itemlist
}


@JvmName("asDomainModelDatabaseItem2")
fun List<DatabaseItem?>.asDomainModelItem(database: GameSitorDatabase): List<Item> {
    return map {
        Item(
            itemId = it!!.itemId,
            cost = it.cost,
            image = it.image,
            name = it.name,
            effects = listOf()
        )
    }
}

fun List<ItemEffectCrossRef?>.asDomainModelEffectRef(database: GameSitorDatabase): List<DatabaseEffect> {

    return map {
        database.effectDao.getEffect(it!!.effectId).value!!
    }
}

@JvmName("asDataBaseModelItem")
fun List<Item>.asDataBaseModelItem(): List<DatabaseItem> {
    return map {
        DatabaseItem(
            itemId = it.itemId,
            cost = it.cost,
            image = it.image,
            name = it.name,
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
        )

    }
}
