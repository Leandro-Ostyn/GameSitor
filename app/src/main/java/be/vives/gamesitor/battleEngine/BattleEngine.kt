package be.vives.gamesitor.battleEngine

import be.vives.gamesitor.constants.ATTACK
import be.vives.gamesitor.constants.DEFENCE
import be.vives.gamesitor.models.Character
import be.vives.gamesitor.models.PlayerLevelHelper
import timber.log.Timber
import java.util.*
import kotlin.math.roundToInt

class BattleEngine {
    private val playerLevelHelper = PlayerLevelHelper()

    fun attack(attacking: Character, defending: Character): Int {
        var hit = 0
        if (!evasion()) {
            hit = calculateHit(attacking, defending)
        }
        Timber.i("The official hit was : %s", hit.toString())
        return hit
    }


    private fun evasion(): Boolean {
        val random = Random()
        val randomNumber = random.nextInt(25)
        if (randomNumber == 5) {
            return true
        }
        return false
    }

    private fun calculateMaxHit(character: Character): Int {
        var rawstrength =
            character.stats.strength * playerLevelHelper.getLevelFromExperience(character.exp)
        rawstrength += 8
        return (0.5 + rawstrength * (calculateBonus(
            "Strength",
            character
        ) + 64) / 640).roundToInt()
    }


    fun calculateBonus(attribute: String, character: Character): Long {
        var bonus = 0L
        for (equipmentItem in character.equipment.items)
            for (itemEffect in equipmentItem.effects) {
                if (itemEffect.attribute == attribute) {
                    bonus += itemEffect.value
                }
            }


        return bonus

    }

    private fun calculateHit(attacking: Character, defending: Character): Int {

        val defenceDefending = calculateBonus(
            DEFENCE,
            defending
        ) + (defending.stats.defence * playerLevelHelper.getLevelFromExperience(defending.exp)).toDouble()
        val attackAttacking = calculateBonus(
            ATTACK,
            attacking
        ) + (attacking.stats.attack * playerLevelHelper.getLevelFromExperience(defending.exp)).toDouble()
        val attkdefbasedhit = attackAttacking /defenceDefending
        var  attkdefstrbasedhit: Double
        if (attkdefbasedhit<=1){
            Timber.i("using the second formula")
        attkdefstrbasedhit=   calculateMaxHit(attacking)*attkdefbasedhit
        }
            attkdefstrbasedhit= (calculateMaxHit(attacking)+attkdefbasedhit)

        val random = Random()
        return random.nextInt(attkdefstrbasedhit.roundToInt())+1
    }
}





