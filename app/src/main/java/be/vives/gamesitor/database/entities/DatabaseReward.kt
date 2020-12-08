package be.vives.gamesitor.database.entities

import androidx.room.Entity

@Entity
data class DatabaseReward (
    var rewardId: Int,
    var item: DatabaseItem,
    var exp: Long
)