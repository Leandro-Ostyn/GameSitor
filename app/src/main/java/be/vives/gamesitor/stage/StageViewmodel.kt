package be.vives.gamesitor.stage

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.database.dbRelationships.crossRefs.EquipmentItemsCrossRef
import be.vives.gamesitor.database.entities.*
import be.vives.gamesitor.database.getDatabase
import be.vives.gamesitor.database.getRepository
import be.vives.gamesitor.gameEngine.BattleEngine
import be.vives.gamesitor.models.Character
import be.vives.gamesitor.models.Item
import be.vives.gamesitor.models.Stage
import timber.log.Timber

class StageViewmodel(application: Application) : AndroidViewModel(application) {
    val battleEngine: BattleEngine = BattleEngine()
    private val database = getDatabase(application)
    private val repository = getRepository(database)

    private val _attacked = MutableLiveData<Boolean>()
    val attacked: LiveData<Boolean> get() = _attacked
    private val _stages = repository.stages
    val stages: LiveData<List<Stage>> get() = _stages
    private val _hpCharacterHero = MutableLiveData<Int>()
    val hpCharacterHero: LiveData<Int> get() = _hpCharacterHero

    private val _enemy = MutableLiveData<Character>()
    val enemy: LiveData<Character> get() = _enemy

    private val _hero = repository.characterFromPlayer
    val hero: LiveData<Character> get() = _hero
    var hpheroStart: Int = 0
    var hpEnemyStart: Int = 0


    private val _gameWon = MutableLiveData<Boolean>()
    val gameWon: LiveData<Boolean> get() = _gameWon
    private val _gameLost = MutableLiveData<Boolean>()
    val gameLost: LiveData<Boolean> get() = _gameLost

    private val _hpCharacterEnemy = MutableLiveData<Int>()
    val hpCharacterEnemy: LiveData<Int> get() = _hpCharacterEnemy
    private val _stageset = MutableLiveData<Stage>()
    val settedStage: LiveData<Stage> get() = _stageset

    init {
        _attacked.value = false
        _gameLost.value = false
        _gameWon.value = false
    }


    fun attack() {
        _enemy.value?.stats?.lifepoints = _enemy.value?.stats?.lifepoints?.minus(
            battleEngine.attack(
                hero.value!!, enemy.value!!

            )
        )!!
        if (_enemy.value?.stats?.lifepoints!! <= 0) {
            gameWon()
        }
        _attacked.value = true
    }

    fun defend() {
        _hero.value?.stats?.lifepoints = _hero.value?.stats?.lifepoints?.minus(
            battleEngine.attack(
                enemy.value!!,
                hero.value!!
            )
        )!!
        Timber.i(" this is hero hp ${hpCharacterHero.value}")
        if (hpCharacterHero.value!! <= 0) {
            gameLost()
        }
        _attacked.value = false
    }

    @JvmName("getStage1")
    fun getStage(stageId: Int): LiveData<DatabaseStage> {
        return repository.getDatabaseStage(stageId)
    }

    fun getAllStages(): LiveData<List<DatabaseStage>> {
        return repository.getDatabaseStages()
    }

    fun getBackGroundFromStage(backgroundId: Int): LiveData<DatabaseBackground> {
        return repository.getBackgroundById(backgroundId)
    }

    fun getRewardFromStage(rewardId: Int): LiveData<DatabaseReward> {
        return repository.getRewardFromStage(rewardId)
    }

    fun getItemById(itemId: Int): LiveData<Item> {
        return repository.getChosenItem(itemId)
    }

    fun getDatabaseCharacter(characterId: Int): LiveData<DatabaseCharacter> {
        return repository.getDatabaseCharacter(characterId)
    }


    fun getStatsById(statsId: Int): LiveData<DatabaseStats> {
        return repository.getStatsById(statsId)
    }

    fun getEquipmentByCharacterId(characterId: Int): LiveData<DatabaseEquipment> {
        return repository.getEquipmentId(characterId)
    }

    fun getCrossRefEquipmentItems(equipmentId: Int): LiveData<List<EquipmentItemsCrossRef>> {
        return repository.getCrossRefEquipmentItems(equipmentId)
    }

    fun getItemIdsFromCrossReffEquipment(equipmentCrossrefList: List<EquipmentItemsCrossRef>): MutableList<Int> {
        val itemIdList = mutableListOf<Int>()
        for (element in equipmentCrossrefList) {
            itemIdList.add(element.itemId)
        }
        return itemIdList
    }

    fun getItemListForChosenList(idList: List<Int>): LiveData<List<Item>> {
        return repository.getFilteredItems(idList)
    }

    fun setStageList(stageList: List<Stage>) {
        repository.setStageList(stageList)
    }

    fun prepareStage(stage: Stage) {
        setEnemy(stage.character)
        setStage(stage)

    }

    fun setStage(stage: Stage) {
        _stageset.postValue(stage)
    }

    fun setEnemy(character: Character) {
        _enemy.postValue(character)
        _hpCharacterEnemy.postValue(character.stats.lifepoints)
        hpEnemyStart = character.stats.lifepoints
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

