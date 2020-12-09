package be.vives.gamesitor.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.vives.gamesitor.database.entities.DatabaseCharacter
import be.vives.gamesitor.database.entities.DatabaseInventory
import be.vives.gamesitor.domain.models.Character
import be.vives.gamesitor.domain.models.Inventory
import be.vives.gamesitor.domain.models.Player
import be.vives.gamesitor.network.SitorApi

@Entity
data class DatabasePlayer constructor(

    @PrimaryKey
    val playerId: Int,
    val Name: String,
    val Password: String,
     val character: Character,
    val Coins: Long,
       val inventory: Inventory,
    val statusPointsLeft: Int,
    val statusPointsAttack: Int,
    val statusPointsDefence: Int,
    val statusPointsStrength: Int,
    val statusPointsHitpoints: Int,
    val EXP: Long
)

fun List<DatabasePlayer>.asDomainModel(): List<Player> {
    return map {

        Player(
            playerId = it.playerId,
            name = it.Name,
            password = it.Password,
           character =   it.character,
            coins = it.Coins,
            inventory = it.inventory,
            statusPointsLeft = it.statusPointsLeft,
            statusPointsAttack = it.statusPointsAttack,
            statusPointsDefence = it.statusPointsDefence,
            statusPointsHitpoints = it.statusPointsHitpoints,
            statusPointsStrength = it.statusPointsStrength,
            EXP = it.EXP
        )
    }
}




