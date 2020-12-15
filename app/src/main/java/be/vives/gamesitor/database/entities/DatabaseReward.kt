package be.vives.gamesitor.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseReward(
    @PrimaryKey
    var rewardId: Int,
    var itemId: Int,
    var exp: Long
)