package be.vives.gamesitor.stageResult

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.constants.MULTIPLIER
import be.vives.gamesitor.constants.getDatabase
import be.vives.gamesitor.constants.getRepository
import be.vives.gamesitor.models.Player
import be.vives.gamesitor.models.PlayerLevelHelper
import be.vives.gamesitor.models.Reward
import be.vives.gamesitor.models.Stage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class StageResultViewModel(application: Application, val rewardId: Int) :
    AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = getRepository(database)
    private val levelHelper = PlayerLevelHelper()
    private val _rewards = repository.rewards
    val rewards: LiveData<List<Reward>> get() = _rewards
    private val _gameWon = MutableLiveData<Boolean>()
    val gameWon: LiveData<Boolean> get() = _gameWon
    private val _stageReward = MutableLiveData<Reward>()
    val stageReward: LiveData<Reward> get() = _stageReward
    private val _player = repository.player
    val player: LiveData<Player> get() = _player
    private val _insertedReward = MutableLiveData<Boolean>()
    val insertedReward: LiveData<Boolean> get() = _insertedReward
    private val _stages = repository.stages
    val stages: LiveData<List<Stage>> get() = _stages

    private val _progressUpdated = MutableLiveData<Boolean>()
    val progressUpdated: LiveData<Boolean> get() = _progressUpdated


    init {
        _progressUpdated.value = false
    }

    fun setGameWon(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _gameWon.postValue(value)
        }
    }


    fun setStageReward(reward: Reward, gameWon: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (gameWon) {
                _stageReward.postValue(reward)
            }
            if (!gameWon) {
                val functReward = Reward(
                    rewardId = reward.rewardId,
                    exp = reward.exp / 2,
                    item = null,
                    coins = reward.coins / 2
                )
                _stageReward.postValue(functReward)
            }
        }
    }

    fun setProgress(player: Player, maxStage: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (player.progress < (maxStage - 1)) {
                player.progress++
            } else {
                player.progress = 0
            }
            progressUpdated()
            repository.setProgressStages(player)

        }
    }

    private fun progressUpdated() {
        _progressUpdated.postValue(true)
    }

    fun insertRewardToPlayer(player: Player, reward: Reward) {
        viewModelScope.launch(Dispatchers.IO) {
            player.coins += reward.coins
            Timber.i("exp before the check function: " + player.character.exp)
            val levelDifference = checkUpdateStatusPointsNeeded(player, reward)
            if (levelDifference > 0) {

                player.statusPointsLeft += (levelDifference * MULTIPLIER)
            }
            if (reward.item != null) {
                player.inventory.items.add(reward.item!!)
            }
            repository.insertRewardToPlayer(player, reward.item)
            insertedReward()
        }
    }

    private fun insertedReward() {
        _insertedReward.postValue(true)
    }

    fun setFalse() {
        _insertedReward.postValue(false)
    }

    private fun checkUpdateStatusPointsNeeded(player: Player, reward: Reward): Int {
        val originalLevel = levelHelper.getLevelFromExperience(player.character.exp)
         player.character.exp += reward.exp
        val updatedLevel = levelHelper.getLevelFromExperience(player.character.exp)
        return updatedLevel - originalLevel
    }

    class StageResultViewModelProvider(val app: Application, val rewardId: Int) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StageResultViewModel::class.java)) {
                return StageResultViewModel(app, rewardId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}