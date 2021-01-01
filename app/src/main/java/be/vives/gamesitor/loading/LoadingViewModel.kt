package be.vives.gamesitor.loading

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.constants.*
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.EquipmentItemsCrossRef
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.InventoryItemsCrossRef
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.TypeItemCrossRef
import be.vives.gamesitor.database.entities.*
import be.vives.gamesitor.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

enum class SitorApiStatus { LOADING, ERROR, DONE }

class LoadingViewModel(app: Application) : AndroidViewModel(app) {
    private val database = getDatabase(app)
    private val repository = getRepository(database)
    private val _gettingDataSuccess = MutableLiveData<Boolean>()
    val gettingDataSuccess: LiveData<Boolean> get() = _gettingDataSuccess
    private val _dbPlayer = repository.dbPlayer
    val dbPlayer: LiveData<DatabasePlayer> get() = _dbPlayer
    private val _items = repository.items
    val items: LiveData<List<Item>> get() = _items
    private val _backgrounds = repository.backgrounds
    val backgrounds: LiveData<List<Background>> get() = _backgrounds
    private val _equipments = repository.equipments
    val equipments: LiveData<List<Equipment>> get() = _equipments
    private val _statsList = repository.statsList
    val statsList: LiveData<List<Stats>> get() = _statsList
    private val _characters = repository.characters
    val characters: LiveData<List<Character>> get() = _characters
    private val _rewards = repository.rewards
    val rewards: LiveData<List<Reward>> get() = _rewards
    private val _inventories = repository.inventories
    val inventories: LiveData<List<Inventory>> get() = _inventories
    private val _player = repository.player
    val player: LiveData<Player> get() = _player
    private val _settings = repository.settings
    val settings: LiveData<DatabaseSettings> get() = _settings
    private val _dbCharacters = repository.getCharacters()
    val dbCharacters: LiveData<List<DatabaseCharacter>> get() = _dbCharacters
    private val _registering = repository.registering
    val registering: LiveData<Boolean> get() = _registering

    //Checking Status of Api Connection
    private val _status = MutableLiveData<SitorApiStatus>()
    val status: LiveData<SitorApiStatus>
        get() = _status

    //Checks to make sure everything runs in the correct sequence.
    private val _itemsAreSet = MutableLiveData<Boolean>()
    val itemsAreSet: LiveData<Boolean> get() = _itemsAreSet
    private val _rewardsAreSet = MutableLiveData<Boolean>()
    val rewardsAreSet: LiveData<Boolean> get() = _rewardsAreSet
    private val _equipmentsAreSet = MutableLiveData<Boolean>()
    val equipmentsAreSet: LiveData<Boolean> get() = _equipmentsAreSet
    private val _charactersAreSet = MutableLiveData<Boolean>()
    val charactersAreSet: LiveData<Boolean> get() = _charactersAreSet
    private val _stagesAreSet = MutableLiveData<Boolean>()
    val stagesAreSet: LiveData<Boolean> get() = _stagesAreSet
    private val _statsAreSet = MutableLiveData<Boolean>()
    val statsAreSet: LiveData<Boolean> get() = _statsAreSet
    private val _backgroundsAreSet = MutableLiveData<Boolean>()
    val backgroundsAreSet: LiveData<Boolean> get() = _backgroundsAreSet
    private val _equipmentUpdated = MutableLiveData<Boolean>()
    val equipmentUpdated: LiveData<Boolean> get() = _equipmentUpdated
    private val _equipmentIdMade = MutableLiveData<Boolean>()
    val equipmentIdMade: LiveData<Boolean> get() = _equipmentIdMade
    private val _domainPlayerIsSet = MutableLiveData<Boolean>()
    val domainPlayerIsSet: LiveData<Boolean> get() = _domainPlayerIsSet


    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = Transformations.map(status) {
        _progress.value = _progress.value!!.plus(1)
        return@map _progress.value

    }

    init {
        _progress.value = 0
        _rewardsAreSet.value = false
        _itemsAreSet.value = false
        _equipmentsAreSet.value = false
        _charactersAreSet.value = false
        _statsAreSet.value = false
        _stagesAreSet.value = false
        _backgroundsAreSet.value = false
        _equipmentUpdated.value = false
        _equipmentIdMade.value = false
        _domainPlayerIsSet.value = false
    }

//private functions to make sure the sequence is correct

    private fun rewardIsSet() {
        _rewardsAreSet.postValue(true)
    }

    private fun itemsIsSet() {
        _itemsAreSet.postValue(true)
    }

    private fun equipmentIsSet() {
        _equipmentsAreSet.postValue(true)
    }

    private fun charactersIsSet() {
        _charactersAreSet.postValue(true)
    }

    private fun statsIsSet() {
        _statsAreSet.postValue(true)
    }

    private fun stagesIsSet() {
        _stagesAreSet.postValue(true)
    }

    private fun backgroundIsSet() {
        _backgroundsAreSet.postValue(true)
    }

    private fun equipmentIsUpdated() {
        _equipmentUpdated.postValue(true)
    }

    private fun equipmentIdIsMade() {
        _equipmentIdMade.postValue(true)
    }

    private fun domainPlayerIsMade() {
        _domainPlayerIsSet.postValue(true)
    }

    //Getting DB Data!!!
    @JvmName("getItems1")
    fun getItems(): LiveData<List<Item>> {
        return repository.getItems()
    }

    fun getTypeItemCrossRef(): LiveData<List<TypeItemCrossRef>> {
        return repository.getTypeItemCrossref()
    }

    @JvmName("getTypes1")
    fun getTypes(): LiveData<List<DatabaseType>> {
        return repository.getTypes()
    }

    @JvmName("getBackgrounds1")
    fun getBackgrounds(): LiveData<List<DatabaseBackground>> {
        return repository.getBackgrounds()
    }

    @JvmName("getRewards1")
    fun getRewards(): LiveData<List<DatabaseReward>> {
        return repository.getRewards()
    }

    fun getStats(): LiveData<List<DatabaseStats>> {
        return repository.getStats()
    }

    fun getEquipmentItemCrossRef(): LiveData<List<EquipmentItemsCrossRef>> {
        return repository.getEquipmentItemCrossRef()
    }

    @JvmName("getEquipments1")
    fun getEquipments(): LiveData<List<DatabaseEquipment>> {
        return repository.getEquipments()
    }

    @JvmName("getCharacters1")
    fun getCharacters(): LiveData<List<DatabaseCharacter>> {
        return repository.getCharacters()
    }

    fun getStages(): LiveData<List<DatabaseStage>> {
        return repository.getStages()
    }

    @JvmName("getInventories1")
    fun getInventories(): LiveData<List<DatabaseInventory>> {
        return repository.getInventories()
    }

    fun getInventoryCrosReffItems(): LiveData<List<InventoryItemsCrossRef>> {
        return repository.getInventoriesItems()
    }

    fun getSettingsByPlayerName(name: String): LiveData<DatabaseSettings> {
        return repository.getSettingsByPlayerName(name)
    }

    //Setting the DB Data to Domain Models
    fun setItems(itemList: List<Item>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setItems(itemList)
            itemsIsSet()
        }
    }

    fun setTypes(
        listDbType: List<DatabaseType>,
        typeItemList: List<TypeItemCrossRef>,
        items: List<Item>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val typeList = mutableListOf<Type>()
            typeList.add(getTypeItems(listDbType, typeItemList, items, WEAPON))
            typeList.add(getTypeItems(listDbType, typeItemList, items, H2WEAPON))
            typeList.add(getTypeItems(listDbType, typeItemList, items, HELMET))
            typeList.add(getTypeItems(listDbType, typeItemList, items, BODY))
            typeList.add(getTypeItems(listDbType, typeItemList, items, SHIELD))
            typeList.add(getTypeItems(listDbType, typeItemList, items, LEGS))
            typeList.add(getTypeItems(listDbType, typeItemList, items, BOOTS))
            repository.setTypes(typeList)
        }
    }

    fun setBackgrounds(dbBackgroundList: List<DatabaseBackground>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setBackgrounds(dbBackgroundList.map {
                Background(
                    backgroundId = it.backgroundId,
                    name = it.name,
                    image = it.image
                )
            })
            backgroundIsSet()
        }
    }

    fun setRewards(dbRewardList: List<DatabaseReward>, itemList: List<Item>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setRewards(dbRewardList.map {
                Reward(
                    rewardId = it.rewardId,
                    exp = it.exp,
                    item = itemList.first { item -> item.itemId == it.itemId },
                    coins = it.coins
                )
            })
            rewardIsSet()
        }
    }

    fun setStats(dbStatsList: List<DatabaseStats>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setStats(dbStatsList.map {
                Stats(
                    statsId = it.statsId,
                    attack = it.attack,
                    defence = it.defence,
                    strength = it.strength,
                    lifepoints = it.lifepoints
                )
            })
            statsIsSet()
        }
    }

    fun setEquipments(
        itemList: List<Item>,
        equipmentCrossRefList: List<EquipmentItemsCrossRef>,
        equipmentList: List<DatabaseEquipment>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val localEquipmentList = mutableListOf<Equipment>()
            for (dbEquipment in equipmentList) {
                val filteredEquipmentCrossRef =
                    equipmentCrossRefList.filter { eCrossreff -> eCrossreff.equipmentId == dbEquipment.equipmentId }
                val localItemList = mutableListOf<Item>()
                for (equipment in filteredEquipmentCrossRef) {
                    localItemList.addAll(itemList.filter { item -> item.itemId == equipment.itemId })
                }
                localEquipmentList.add(
                    Equipment(
                        equipmentId = dbEquipment.equipmentId,
                        name = dbEquipment.name,
                        characterId = dbEquipment.characterId,
                        items = localItemList
                    )
                )
            }
            repository.setEquipments(localEquipmentList)
            equipmentIsSet()
        }
    }

    fun setCharacter(
        dbCharacterList: List<DatabaseCharacter>,
        equipmentList: List<Equipment>,
        statslist: List<Stats>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val characterList = mutableListOf<Character>()
            for (dbCharacter in dbCharacterList) {
                val stats = statslist.first { stats -> stats.statsId == dbCharacter.statsId }
                val equipment =
                    equipmentList.first { equipment -> equipment.characterId == dbCharacter.characterId }
                characterList.add(
                    Character(
                        IsHero = dbCharacter.isHero,
                        characterId = dbCharacter.characterId,
                        equipment = equipment,
                        stats = stats,
                        exp = dbCharacter.exp,
                        name = dbCharacter.name,
                        image = dbCharacter.image
                    )
                )
            }
            repository.setCharacters(characterList)
            charactersIsSet()
        }
    }

    fun setStages(
        dbStageList: List<DatabaseStage>,
        backgroundList: List<Background>,
        characterList: List<Character>,
        rewardList: List<Reward>
    ) {
        viewModelScope.launch {
            val stageList = mutableListOf<Stage>()
            for (dbstage in dbStageList) {
                val background =
                    backgroundList.first { background -> background.backgroundId == dbstage.backgroundId }
                val character =
                    characterList.first { character -> character.characterId == dbstage.characterId }
                val reward = rewardList.first { reward -> reward.rewardId == dbstage.rewardId }
                stageList.add(
                    Stage(
                        stageId = dbstage.stageId,
                        background = background,
                        name = dbstage.name,
                        character = character,
                        reward = reward
                    )
                )
            }
            repository.setStages(stageList)
            stagesIsSet()
        }
    }

    fun setInventories(
        itemList: List<Item>,
        dbInventoryList: List<DatabaseInventory>,
        inventoryItemsCrossRefList: List<InventoryItemsCrossRef>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val inventoryList = mutableListOf<Inventory>()
            for (dbInventory in dbInventoryList) {
                val crosrefflist =
                    inventoryItemsCrossRefList.filter { crossref -> crossref.inventoryId == dbInventory.inventoryId }
                val localItemList = mutableListOf<Item>()
                for (crossref in crosrefflist) {
                    localItemList.addAll(itemList.filter { item -> item.itemId == crossref.itemId })
                }
                inventoryList.add(
                    Inventory(
                        inventoryId = dbInventory.inventoryId,
                        items = localItemList
                    )
                )
            }
            repository.setInventories(inventoryList)
        }
    }

    fun setPlayer(
        dbPlayer: DatabasePlayer,
        characters: List<Character>,
        inventoryList: List<Inventory>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setPlayer(
                Player(
                    playerId = dbPlayer.playerId,
                    name = dbPlayer.name!!,
                    password = dbPlayer.password!!,
                    character = characters.first { character -> character.characterId == dbPlayer.characterId },
                    coins = dbPlayer.coins,
                    inventory = inventoryList.first { inventory -> inventory.inventoryId == dbPlayer.inventoryId },
                    statusPointsLeft = dbPlayer.statusPointsLeft,
                    statusPointsAttack = dbPlayer.statusPointsAttack,
                    statusPointsDefence = dbPlayer.statusPointsDefence,
                    statusPointsStrength = dbPlayer.statusPointsStrength,
                    statusPointsHitpoints = dbPlayer.statusPointsHitpoints,
                    progress = dbPlayer.progress
                )
            )
            domainPlayerIsMade()
        }
    }

    fun setSettings(settings: DatabaseSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setSettings(settings)
        }
    }

    fun createSettings(name: String, password: String) {
        viewModelScope.launch {
            val settings = DatabaseSettings(
                settingId = 0,
                hideAnimations = true,
                musicOn = false,
                playerName = name,
                passWord = password,
                setNotification = true
            )
            repository.createSettings(settings)
        }
    }


    //MappingFunctions from DB to Domain
    private fun getTypeItems(
        listDbType: List<DatabaseType>,
        typeItemList: List<TypeItemCrossRef>,
        items: List<Item>,
        type: String
    ): Type {
        val type = listDbType.first { dbType -> dbType.type == type }
        val filteredTypeItems =
            typeItemList.filter { typeItem -> typeItem.typeId == type.typeId }
        val typeItems = mutableListOf<Item>()
        for (item in items) {
            for (typeitem in filteredTypeItems) {
                if (item.itemId == typeitem.itemId) {
                    typeItems.add(item)
                }
            }
        }
        return Type(type.typeId, type.type, typeItems)
    }

    //Methods To set Succes on getting API resources
    fun setSuccess() {
        _gettingDataSuccess.postValue(true)
    }

    fun setFailure() {
        _gettingDataSuccess.postValue(false)
    }

    //NetworkConnection
    fun setNetWorkConnection(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) { repository.setNetwork(value) }
    }


    //1st login after Register Methods - > Create character,Stats,Equipment for Player

    fun makeCharacterForPlayer(
        dbPlayer: DatabasePlayer,
        characterList: List<DatabaseCharacter>,
        statsId: String,
        equipmentId: String
    ): MutableLiveData<String> {
        val characterId = MutableLiveData<String>()
        viewModelScope.launch(Dispatchers.IO) {
            val characterForPlayer = characterList.first { character -> character.name == PLAYER }

            characterId.postValue(
                repository.makeCharacterForPlayer(
                    dbPlayer,
                    characterForPlayer,
                    statsId,
                    equipmentId
                )
            )
        }
        return characterId
    }

    fun createStatsForPlayerCharacter(): MutableLiveData<String> {
        val inventoryId = MutableLiveData<String>()
        viewModelScope.launch {
            inventoryId.postValue(repository.createStatsForCharacter())
        }
        return inventoryId
    }

    fun makeEquipmentForPlayerCharacter(dbPlayer: DatabasePlayer): MutableLiveData<String> {
        val equipmentId = MutableLiveData<String>()
        viewModelScope.launch(Dispatchers.IO) {
            val localEquipmentId = repository.makeEquipmentForPlayer(dbPlayer)
            equipmentId.postValue(localEquipmentId)
            equipmentIdIsMade()
        }
        return equipmentId
    }

    fun updateEquipmentForPlayerCharacter(characterId: String, equipmentId: String) {
        viewModelScope.launch {
            repository.updateEquipmentForPlayerCharacter(characterId, equipmentId)
            equipmentIsUpdated()
        }
    }

    fun stopRegistering() {
        viewModelScope.launch(Dispatchers.IO) { repository.stopRegistering() }
    }

    fun startRegistering() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setRegistering()
        }
    }

    //Getting All api resources
    fun refreshAllDataFromApi() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshBackgrounds()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshCategories()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshCategoryTypes()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshCharacterList()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshEffects()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshEffectLists()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshEquipmentList()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshEquipmentItemsList()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshItems()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshRewards()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshShops()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshStage()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshStats()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshStocks()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshTypes()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                try {
                    _status.postValue(SitorApiStatus.LOADING)
                    repository.refreshTypeItems()
                    _status.postValue(SitorApiStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(SitorApiStatus.ERROR)
                }
                setSuccess()
            }
        } catch (e: Exception) {
            setFailure()
        }

    }

    class LoadingViewModelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoadingViewModel::class.java)) {
                return LoadingViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}