package be.vives.gamesitor.network.entities.dbmappingentities

import be.vives.gamesitor.network.entities.Type

//IS only needed if mapping directly from DB
data class CategoryType (
    val categoryTypeId:Int,
    val type: Type
        )