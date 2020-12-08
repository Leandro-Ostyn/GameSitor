package be.vives.gamesitor.network.entities

data class Reward (
    var rewardId: Int,
    var item: Item,
    var exp: Long
)