package be.vives.gamesitor.models

import androidx.room.Embedded

data class Reward (
    var rewardId: Int,
    @Embedded
    var item: Item,
    var exp: Long
)