package be.vives.gamesitor.stage

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.database.entities.DatabaseStage
import be.vives.gamesitor.database.getDatabase
import be.vives.gamesitor.repository.Repository
import be.vives.gamesitor.gameEngine.BattleEngine
import be.vives.gamesitor.models.Stage

class StageViewmodel(application: Application) : AndroidViewModel(application) {
    val battleEngine: BattleEngine = BattleEngine()
    private val database = getDatabase(application)
    private val repository = Repository(database)

    private val _attacked = MutableLiveData<Boolean>()
    val attacked: LiveData<Boolean> get() = _attacked
    private val _stage = MutableLiveData<DatabaseStage>()
    val stage: LiveData<DatabaseStage> get() = _stage
    private val _HpCharachterHero = MutableLiveData<Int>()
    val hpCharacterHero: LiveData<Int> get() = _HpCharachterHero
    var hpheroStart: Int = 0
    var hpEnemyStart: Int = 0

    private val _gameWon = MutableLiveData<Boolean>()
    val gameWon: LiveData<Boolean> get() = _gameWon
    private val _gameLost = MutableLiveData<Boolean>()
    val gameLost: LiveData<Boolean> get() = _gameLost

    private val _hpCharacterEnemy = MutableLiveData<Int>()
    val hpCharacterEnemy: LiveData<Int> get() = _hpCharacterEnemy
    private val stageset = repository.getStage(1)

    init {
        _stage.value = repository.getStage(1).value
        _attacked.value = false
        _gameLost.value = false
        _gameWon.value = false
//        _HpCharachterHero.value = stageCharacter.characterB.stats.lifepoints
//        _hpCharacterEnemy.value = stageCharacter.characterA.stats.lifepoints
        hpheroStart = _HpCharachterHero.value!!
        hpEnemyStart = _hpCharacterEnemy.value!!
    }


    private val _typeId: MutableLiveData<Int> = MutableLiveData()
    //val items = repository.items

    val backgrounds = repository.backgrounds

//    fun calculateDamageToEnemy() {
//        _hpCharacterEnemy.value = _hpCharacterEnemy.value?.minus(
//            battleEngine.attack(
//                stageCharacter.characterB,
//                stageCharacter.characterA
//            )
//        )
//        if (hpCharacterEnemy.value!! <= 0) {
//            gameWon()
//        }
//        _attacked.value = true
//    }
//
//    fun calculateDamageToHero() {
//        _HpCharachterHero.value = _HpCharachterHero.value?.minus(
//            battleEngine.attack(
//                stageCharacter.characterA,
//                stageCharacter.characterB
//            )
//        )
//        Timber.i(" this is hero hp ${hpCharacterHero.value}")
//        if (hpCharacterHero.value!! <= 0) {
//            gameLost()
//        }
//        _attacked.value = false
//    }

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

