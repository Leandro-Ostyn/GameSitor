package be.vives.gamesitor.stats

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.constants.*
import be.vives.gamesitor.models.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.IllegalArgumentException

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = getRepository(database)

    private val _player = repository.player
    val player: LiveData<Player> get() = _player


    fun updateStatusPoints(attribute: String, action: String, player: Player) {
        viewModelScope.launch(Dispatchers.IO) {
            if (action == ADD) {
                if (player.statusPointsLeft >= 1) {
                    repository.updateStatusPoints(addAttributes(attribute, player))
                    repository.updateStatsPlayer(addLevelFromAttribute(attribute, player))
                }
            } else if (action == REMOVE) {
                repository.updateStatsPlayer(deleteLevelFromAttribute(attribute, player))
                repository.updateStatusPoints(deleteAttributes(attribute, player))

            }

        }
    }

    private fun addAttributes(attribute: String, player: Player): Player {
        when (attribute) {
            ATTACK -> {
                player.statusPointsLeft--
                player.statusPointsAttack++
            }
            DEFENCE -> {
                player.statusPointsLeft--
                player.statusPointsDefence++
            }
            STRENGTH -> {
                player.statusPointsLeft--
                player.statusPointsStrength++
            }
            HITPOINTS -> {
                player.statusPointsLeft--
                player.statusPointsHitpoints++
            }
        }
        return player
    }

    private fun deleteAttributes(attribute: String, player: Player): Player {
        when (attribute) {
            ATTACK -> {
                if (player.statusPointsAttack >= 1) {
                    player.statusPointsLeft++
                    player.statusPointsAttack--
                }
            }
            DEFENCE -> {
                if (player.statusPointsDefence >= 1) {
                    player.statusPointsLeft++
                    player.statusPointsDefence--
                }
            }
            STRENGTH -> {
                if (player.statusPointsStrength >= 1) {
                    player.statusPointsLeft++
                    player.statusPointsStrength--
                }
            }
            HITPOINTS -> {
                if (player.statusPointsHitpoints >= 1) {
                    player.statusPointsLeft++
                    player.statusPointsHitpoints--
                }
            }
        }
        return player
    }

    private fun addLevelFromAttribute(attribute: String, player: Player): Player {
        when (attribute) {
            ATTACK -> {
                player.character.stats.attack++
            }
            DEFENCE -> {
                player.character.stats.defence++
            }
            STRENGTH -> {
                player.character.stats.strength++
            }
            HITPOINTS -> {
                player.character.stats.lifepoints++
            }
        }

        return player
    }

    private fun deleteLevelFromAttribute(attribute: String, player: Player): Player {
        when (attribute) {
            ATTACK -> {
                if (player.statusPointsAttack >= 1) {
                    player.character.stats.attack--
                }
            }
            DEFENCE -> {
                if (player.statusPointsDefence >= 1) {
                    player.character.stats.defence--
                }
            }
            STRENGTH -> {
                if (player.statusPointsStrength >= 1) {
                    player.character.stats.strength--
                }
            }
            HITPOINTS -> {
                if (player.statusPointsHitpoints >= 1) {
                    player.character.stats.lifepoints--
                }
            }
        }
        return player
    }

    class StatsViewModelFactory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
                return StatsViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown viewModel Class")
        }
    }
}