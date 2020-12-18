package be.vives.gamesitor.gameEngine

import be.vives.gamesitor.models.Character
import timber.log.Timber
import java.util.*

class BattleEngine {
    //Nog verder uitwerken met equipment, en items.

//    fun calculatePoison(Poison: Boolean, Grade: Int): Boolean {
//        if (Grade == 1) {
//
//        }
//        return true
//    }

//    fun levelAttackRecacalculation(character: DatabaseCharacter): Int {
//        var attackRecalculation =
//            (character.level * character.stats.attack) * (character.level * character.stats.strength)
//
//        return attackRecalculation
//    }
//
//    fun levelDefenceRecalculation(character: DatabaseCharacter): Int {
//        var defendingRecalculation =
//            (character.level * character.stats.defence)
//        return defendingRecalculation
//    }

    //    fun equipmentCalculator(character: DatabaseCharacter) {
//        if (character.equipment != null) {
//            character.stats.attack += character.equipment!!.weapon.effects.get(1).effect.value.toInt()
//            //    character.stats.defence += character.equipment!!.shield.category.effectValue.toInt() + character.equipment!!.body.category.effectValue.toInt() + character.equipment!!.legs.category.effectValue.toInt()
//            //   character.stats.strength += character.equipment!!.Boots.category.effectValue.toInt()
//        }
//    }
    fun attack(attacking: Character, defending: Character): Int {
        var hit = 0
        if (!evasion()) {
            Timber.i("Not evaded")
            hit = calculateHit(attacking,defending)
            Timber.i(hit.toString() + "this is te actual hit (old formula)")
            return hit

        }
        Timber.i("evaded")

        return hit
    }



    fun evasion(): Boolean {
        val random = Random()
        val randomNumber = random.nextInt(15)
        if (randomNumber == 5) {
            return true
        }
        return false
    }

    fun calculateMaxHit(character: Character): Int {
        var rawstrength = character.stats.strength * character.level
        //This is for correct calculation
        rawstrength += 8
        Timber.i(rawstrength.toString())
        val calculation = Math.round(
            0.5 + rawstrength * (calculateBonus(
                "Strength",
                character
            ) + 64) / 640
        )
        return calculation.toInt()
    }


    fun calculateBonus(attribute: String, character: Character): Long {
        var bonus = 0L
        if (character.equipment != null) {
            for (equipmentItem in character.equipment.items!!)
//                for (itemEffect in equipmentItem.effects) {
//                    if (itemEffect!!.attribute == attribute) {
//                        bonus += itemEffect.value
//                    }
    Timber.i("")
                }

        return bonus
    }

    fun calculateHit(character: Character, defending: Character): Int {
        Timber.i("attack and def : "+character.stats.attack.toDouble()+"  "+defending.stats.defence.toDouble())
      var procent=  character.stats.attack.toDouble()/defending.stats.defence.toDouble()
        Timber.i("original proc: $procent")
    if(procent<1){
            procent*=100
            Timber.i("recalc proc: $procent")

      }
        var attkdefbasedhit = (calculateMaxHit(character)+(calculateMaxHit(character)*procent))/2

        val random = Random()
        val calculatewithall = calculateMaxHit(character)+attkdefbasedhit
        return random.nextInt(calculateMaxHit(character) + 1)


    }
}




