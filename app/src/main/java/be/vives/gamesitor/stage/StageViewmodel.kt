package be.vives.gamesitor.stage

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.constants.getDatabase
import be.vives.gamesitor.constants.getRepository
import be.vives.gamesitor.battleEngine.BattleEngine
import be.vives.gamesitor.constants.HITPOINTS
import be.vives.gamesitor.models.Character
import be.vives.gamesitor.models.Player
import be.vives.gamesitor.models.Stage
import timber.log.Timber
import java.util.logging.Handler

class StageViewmodel(application: Application) : AndroidViewModel(application) {
    private val battleEngine: BattleEngine = BattleEngine()
    private val database = getDatabase(application)
    private val repository = getRepository(database)
    private val _attacked = MutableLiveData<Boolean>()
    val attacked: LiveData<Boolean> get() = _attacked
    private val _stages = repository.stages
    val stages: LiveData<List<Stage>> get() = _stages
    private val _player = repository.player
    val player: LiveData<Player> get() = _player

    private val _enemy = MutableLiveData<Character>()
    val enemy: LiveData<Character> get() = _enemy
    private val _hero = MutableLiveData<Character>()
    val hero: LiveData<Character> get() = _hero


    private val _hpHeroStart = MutableLiveData<Int>()
    val hpheroStart: LiveData<Int> get() = _hpHeroStart

    private val _hpHero = MutableLiveData<Int>()
    val hphero: LiveData<Int> get() = _hpHero

    private val _hpEnemy = MutableLiveData<Int>()
    val hpEnemy: LiveData<Int> get() = _hpEnemy

    private val _hpEnemyStart = MutableLiveData<Int>()
    val hpEnemyStart: LiveData<Int> get() = _hpEnemyStart


    private val _gameWon = MutableLiveData<Boolean>()
    val gameWon: LiveData<Boolean> get() = _gameWon
    private val _gameLost = MutableLiveData<Boolean>()
    val gameLost: LiveData<Boolean> get() = _gameLost
    private val _stageset = MutableLiveData<Stage>()
    val settedStage: LiveData<Stage> get() = _stageset

    init {
        _attacked.value = false
        _gameLost.value = false
        _gameWon.value = false
    }


    fun attack() {
       _hpEnemy.postValue( _hpEnemy.value?.minus(
            battleEngine.attack(
                hero.value!!, enemy.value!!

            )
        ))
        if (_hpEnemy .value!! <= 0) {
            gameWon()
        }
        _attacked.value = true
    }

    fun defend() {
        _attacked.value = false
        _hpHero.postValue( _hpHero.value?.minus(
            battleEngine.attack(
                enemy.value!!,
                hero.value!!
            )
        ))
        if (_hpHero.value!! <= 0) {
            gameLost()
        }

    }

    fun prepareStage(stage: Stage, player: Player) {
        setHero(player.character)
        setEnemy(stage.character)
        setStage(stage)

    }

    private fun setStage(stage: Stage) {
        _stageset.postValue(stage)
    }

    private fun setEnemy(character: Character) {
      val hitPointsBonus=  battleEngine.calculateBonus(HITPOINTS,character)
        character.stats.lifepoints += hitPointsBonus.toInt()
        _enemy.postValue(character)
        _hpEnemyStart.postValue(character.stats.lifepoints)
        _hpEnemy.postValue(character.stats.lifepoints)
    }

    private fun setHero(character: Character) {
        val hitPointsBonus=  battleEngine.calculateBonus(HITPOINTS,character)
        character.stats.lifepoints += hitPointsBonus.toInt()
        _hero.postValue(character)
        _hpHeroStart.postValue(character.stats.lifepoints)
        _hpHero.postValue(character.stats.lifepoints)
    }

    private fun gameWon() {
        _gameWon.value = true
    }

    private fun gameLost() {
        _gameLost.value = true
    }

    class StageViewmodelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StageViewmodel::class.java)) {
                return StageViewmodel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

