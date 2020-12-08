package be.vives.gamesitor.network.dbmappingentities

import be.vives.gamesitor.domain.models.Type

//IS only needed if mapping directly from DB
data class CategoryType (
    val categoryTypeId:Int,
    val type: Type
        )