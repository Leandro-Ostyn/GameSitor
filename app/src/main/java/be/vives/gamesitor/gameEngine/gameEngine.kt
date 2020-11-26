package be.vives.gamesitor.gameEngine

import be.vives.gamesitor.model.Character

class gameEngine {

    fun CalculateDamage(Attacking: Character, Defending: Character): Int {
        val attackingDmge = Attacking.stats.attack * Attacking.stats.strength
        val defenceReducing = attackingDmge / Defending.stats.defence
        return defenceReducing
    }
}