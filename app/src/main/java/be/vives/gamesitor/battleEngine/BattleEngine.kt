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

        val defence = calculateBonus(
            DEFENCE,
            defending
        ) + (defending.stats.defence * playerLevelHelper.getLevelFromExperience(defending.exp))
        val attack = calculateBonus(ATTACK, attacking) + (attacking.stats.attack* playerLevelHelper.getLevelFromExperience(defending.exp))
        var procent = defence.toDouble() / attack.toDouble()
        var attkdefbasedhit = 0.0
        if (procent <= 0) {
            attkdefbasedhit =
                (calculateMaxHit(attacking) + (calculateMaxHit(attacking) * procent)) / 2
        } else if (procent >= 0) {
          attkdefbasedhit=  (calculateMaxHit(attacking) + (calculateMaxHit(attacking) * procent)) / 2
        }
Timber.i(procent.toString() + "this is the procent")
        if (attkdefbasedhit<=1){
            attkdefbasedhit = 1.0
        }
        Timber.i("%s this should be the new calculated hits", attkdefbasedhit.toString())
        Timber.i("%s this is the current hit", calculateMaxHit(attacking))
        val random = Random()

        return random.nextInt(attkdefbasedhit.toInt())
    }
}




