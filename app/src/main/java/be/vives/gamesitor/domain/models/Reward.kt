package be.vives.gamesitor.domain.models

data class Reward (
    var rewardId: Int,
    var item: Item,
    var exp: Long
)