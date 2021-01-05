package be.vives.gamesitor.models

import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class PlayerLevelHelper {
    fun getLevelFromExperience(experience: Long): Int {
        return if (experience <= 0) {
            1
        } else ((sqrt((100 * (2 * experience + 25)).toDouble()) + 50) / 100).roundToInt()
    }

    fun getExperienceFromLevel(level: Int): Int {
        return if (level <= 0) {
            0
        } else (level.toDouble().pow(2.0).toInt() + level) / 2 * 100 - level * 100
    }

    fun getExperienceForNextLevel(experience: Long): Int {
        val currentLevel = getLevelFromExperience(experience)
        val nextLevel = currentLevel + 1
        return getExperienceFromLevel(nextLevel)
    }

    fun getRemainingExperienceUntilNextLevel(experience: Long): Long {
        val experienceForNextLevel = getExperienceForNextLevel(experience)
        return experienceForNextLevel - experience
    }
}
