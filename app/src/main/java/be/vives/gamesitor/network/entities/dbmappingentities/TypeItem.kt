package be.vives.gamesitor.network.entities.dbmappingentities

import be.vives.gamesitor.network.entities.Item

//IS only needed if mapping directly from DB
data class TypeItem(
    val typeItemId:Int,
    val Item: Item
)
