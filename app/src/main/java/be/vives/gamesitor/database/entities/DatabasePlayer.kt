package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabasePlayer(

    @PrimaryKey(autoGenerate = true)
    val playerId: Int,
    val name: String?,
    val password: String?,
    var characterId: String,
    val coins: Long,
    val inventoryId: String,
    val statusPointsLeft: Int,
    val statusPointsAttack: Int,
    val statusPointsDefence: Int,
    val statusPointsStrength: Int,
    val statusPointsHitpoints: Int,
    val progress: Int
)





