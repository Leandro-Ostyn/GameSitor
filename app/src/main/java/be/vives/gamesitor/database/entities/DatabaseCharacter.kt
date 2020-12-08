package be.vives.gamesitor.database.entities

import be.vives.gamesitor.database.DatabasePlayer
import be.vives.gamesitor.domain.models.Character
import be.vives.gamesitor.domain.models.Player

data class DatabaseCharacter (
    var characterId : Int,
    var name: String,
    var level: Int,
    var equipment : DatabaseEquipment,
    var stats : DatabaseStats

)
fun List<DatabaseCharacter>.asDomainModel(): List<Character> {
    return map {
      Character(
          characterId = it.characterId,
          name = it.name,
          level = it.level,
          equipment = it.equipment,
          stats = it.stats
      )
        )
    }
}