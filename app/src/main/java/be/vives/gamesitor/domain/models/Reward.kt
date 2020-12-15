package be.vives.gamesitor.domain.models

import androidx.room.Embedded

data class Reward (
    var rewardId: Int,
    @Embedded   var item: ItemWithEffect,
    var exp: Long
)