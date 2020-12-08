package be.vives.gamesitor.network.dbmappingentities

import be.vives.gamesitor.domain.models.Item


//IS only needed if mapping directly from DB

data class EquipmentItems(
    var equipmentItemsId :Int,
    var item : Item
)
