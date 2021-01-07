package be.vives.gamesitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.vives.gamesitor.constants.*
import be.vives.gamesitor.database.SitorDatabase
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.EquipmentItemsCrossRef
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.InventoryItemsCrossRef
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.TypeItemCrossRef
import be.vives.gamesitor.database.entities.*
import be.vives.gamesitor.models.*
import be.vives.gamesitor.network.SitorApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*


class Repository(private val database: SitorDatabase) {
    private val _playerWeaponList = MutableLiveData<List<Item>>()
    val playerWeaponList: LiveData<List<Item>> get() = _playerWeaponList
    private val _player2HWeaponList = MutableLiveData<List<Item>>()
    val player2HWeaponList: LiveData<List<Item>> get() = _player2HWeaponList
    private val _playerHelmetList = MutableLiveData<List<Item>>()
    val playerHelmetList: LiveData<List<Item>> get() = _playerHelmetList
    private val _playerBodyList = MutableLiveData<List<Item>>()
    val playerBodyList: LiveData<List<Item>> get() = _playerBodyList
    private val _playerLegsList = MutableLiveData<List<Item>>()
    val playerLegsList: LiveData<List<Item>> get() = _playerLegsList
    private val _playerShieldList = MutableLiveData<List<Item>>()
    val playerShieldList: LiveData<List<Item>> get() = _playerShieldList
    private val _playerBootsList = MutableLiveData<List<Item>>()
    val playerBootsList: LiveData<List<Item>> get() = _playerBootsList
    private val _dbPlayer = MutableLiveData<DatabasePlayer>()
    val dbPlayer: LiveData<DatabasePlayer> get() = _dbPlayer

    //controle if player logs in for the first time
    private val _registering = MutableLiveData<Boolean>()
    val registering: LiveData<Boolean> get() = _registering


    //Everything for loading Screen Variables :

    private val _items = MutableLiveData<List<Item>>() //check
    val items: LiveData<List<Item>> get() = _items
    private val _types = MutableLiveData<List<Type>>() //check
    val types: LiveData<List<Type>> get() = _types
    private val _backgrounds = MutableLiveData<List<Background>>()
    val backgrounds: LiveData<List<Background>> get() = _backgrounds
    private val _rewards = MutableLiveData<List<Reward>>()
    val rewards: LiveData<List<Reward>> get() = _rewards
    private val _statsList = MutableLiveData<List<Stats>>()
    val statsList: LiveData<List<Stats>> get() = _statsList
    private val _equipments = MutableLiveData<List<Equipment>>()
    val equipments: LiveData<List<Equipment>> get() = _equipments
    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters
    private val _stages = MutableLiveData<List<Stage>>()
    val stages: LiveData<List<Stage>> get() = _stages
    private val _inventories = MutableLiveData<List<Inventory>>()
    val inventories: LiveData<List<Inventory>> get() = _inventories
    private val _player = MutableLiveData<Player>()
    val player: LiveData<Player> get() = _player

    //This livedata is to check every page with internet connection, but in this current state of game its not needed.
    private val _networkConnection = MutableLiveData<Boolean>()
    val networkConnection: LiveData<Boolean> get() = _networkConnection
    private val _settings = MutableLiveData<DatabaseSettings>()
    val settings: LiveData<DatabaseSettings> get() = _settings


    init {
        _registering.value = false
    }

    //Everything for Loading screen to Fill All variables.


    //Getters From Db ------------------------------------------------------------------------------------------------- GETTER SECTION --------------------------------------------------------------------------------------------
    @JvmName("getItems1")
    fun getItems(): LiveData<List<Item>> {
        return database.itemDao.getAllItemsWithEffects()
    }

    fun getTypeItemCrossref(): LiveData<List<TypeItemCrossRef>> {
        return database.typeDao.getCrossReff()
    }

    @JvmName("getTypes1")
    fun getTypes(): LiveData<List<DatabaseType>> {
        return database.typeDao.getTypes()
    }

    fun getChosenItem(itemId: Int): LiveData<Item> {
        return database.itemDao.getItemsWithEffects(itemId)
    }

    @JvmName("getBackgrounds1")
    fun getBackgrounds(): LiveData<List<DatabaseBackground>> {
        return database.backgroundDao.getBackgrounds()
    }

    @JvmName("getRewards1")
    fun getRewards(): LiveData<List<DatabaseReward>> {
        return database.rewardDao.getRewards()
    }

    fun getStats(): LiveData<List<DatabaseStats>> {
        return database.statsDao.getAllStats()
    }

    @JvmName("getEquipments1")
    fun getEquipments(): LiveData<List<DatabaseEquipment>> {
        return database.equipmentDao.getEquipments()
    }

    fun getEquipmentItemCrossRef(): LiveData<List<EquipmentItemsCrossRef>> {
        return database.equipmentDao.getEquipmentItemsCrossRef()
    }

    @JvmName("getCharacters1")
    fun getCharacters(): LiveData<List<DatabaseCharacter>> {
        return database.characterDao.getCharacters()
    }

    @JvmName("getStages1")
    fun getStages(): LiveData<List<DatabaseStage>> {
        return database.stageDao.getStages()
    }


    @JvmName("getInventories1")
    fun getInventories(): LiveData<List<DatabaseInventory>> {
        return database.inventoryDao.getInventories()
    }

    fun getInventoriesItems(): LiveData<List<InventoryItemsCrossRef>> {
        return database.inventoryDao.getAllCrossReffInventoryItems()
    }

    fun getPlayer(name: String): LiveData<DatabasePlayer> {
        return database.playerDao.getPlayerByUserName(name)
    }

    fun getSettingsByPlayerName(name: String): LiveData<DatabaseSettings> {
        return database.settingsDao.getSettingsByPlayerName(name)
    }

    //Setters to map from db to Domain Models. ------------------------------------------------------------------------------------------------- SETTER SECTION --------------------------------------------------------------------------------------------
    suspend fun setItems(itemList: List<Item>) {
        withContext(Dispatchers.IO) {
            _items.postValue(itemList)
        }
    }

    suspend fun setTypes(typeList: List<Type>) {
        withContext(Dispatchers.IO) {
            _types.postValue(typeList)
        }
    }

    suspend fun setBackgrounds(backgroundList: List<Background>) {
        withContext(Dispatchers.IO) {
            _backgrounds.postValue(backgroundList)
        }
    }

    suspend fun setRewards(rewardList: List<Reward>) {
        withContext(Dispatchers.IO) {
            _rewards.postValue(rewardList)

        }
    }

    suspend fun setStats(statsList: List<Stats>) {
        withContext(Dispatchers.IO) {
            _statsList.postValue(statsList)
        }
    }

    suspend fun setEquipments(equipmentList: List<Equipment>) {
        withContext(Dispatchers.IO) {
            _equipments.postValue(equipmentList)
        }
    }

    suspend fun setCharacters(characterList: List<Character>) {
        withContext(Dispatchers.IO) {
            _characters.postValue(characterList)
        }
    }

    suspend fun setStages(stageList: List<Stage>) {
        withContext(Dispatchers.IO) {
            _stages.postValue(stageList)
        }
    }

    suspend fun setInventories(inventoryList: List<Inventory>) {
        withContext(Dispatchers.IO) {
            _inventories.postValue(inventoryList)
        }
    }

    suspend fun setPlayer(player: Player) {
        withContext(Dispatchers.IO) {
            _player.postValue(player)
        }
    }

    suspend fun setDbPlayer(databasePlayer: DatabasePlayer) {
        withContext(Dispatchers.IO) { _dbPlayer.postValue(databasePlayer) }
    }

    suspend fun setSettings(settings: DatabaseSettings) {
        withContext(Dispatchers.IO) { _settings.postValue(settings) }
    }

    suspend fun setRegistering() {
        withContext(Dispatchers.IO) {
            _registering.postValue(true)
        }
    }

    suspend fun createSettings(settings: DatabaseSettings) {
        withContext(Dispatchers.IO) {
            _settings.postValue(settings)
            database.settingsDao.insertAll(settings)
        }
    }

    //Functions To update the liveData Models   ------------------------------------------------------------------------------------------------- UPDATE SECTION --------------------------------------------------------------------------------------------
    suspend fun removeEquippedItem(equipmentId: String, itemId: Int) {
        withContext(Dispatchers.IO) { database.equipmentDao.removeCrossReff(itemId, equipmentId) }
    }


    fun returnChosenEquipmentList(type: String): LiveData<List<Item>> {
        return when (type) {
            WEAPON -> playerWeaponList
            H2WEAPON -> player2HWeaponList
            HELMET -> playerHelmetList
            BODY -> playerBodyList
            LEGS -> playerLegsList
            SHIELD -> playerShieldList
            else -> {
                playerBootsList
            }
        }

    }

    suspend fun insertItemToInventory(itemId: Int, player: Player) {
        withContext(Dispatchers.IO) {
            database.playerDao.updatePlayerCash(player.coins, player.playerId)
            database.inventoryDao.insertCrossReff(itemId, player.inventory.inventoryId)
        }
    }

    suspend fun deleteItemFromInventory(player: Player, itemId: Int) {
        withContext(Dispatchers.IO) {
            database.playerDao.updatePlayerCash(player.coins, player.playerId)
            database.inventoryDao.deleteCrossReff(player.inventory.inventoryId, itemId)
        }
    }

    suspend fun setFilteredListForEquipment(itemList: List<Item>, type: String) {
        withContext(Dispatchers.IO) {
            when (type) {
                WEAPON -> _playerWeaponList.postValue(itemList)
                H2WEAPON -> _player2HWeaponList.postValue(itemList)
                HELMET -> _playerHelmetList.postValue(itemList)
                BODY -> _playerBodyList.postValue(itemList)
                LEGS -> _playerLegsList.postValue(itemList)
                SHIELD -> _playerShieldList.postValue(itemList)
                BOOTS -> _playerBootsList.postValue(itemList)
            }
        }
    }


    suspend fun equipToPlayer(itemId: Int, player: Player) {
        withContext(Dispatchers.IO) {
            val crossref = EquipmentItemsCrossRef(
                0,
                equipmentId = player.character.equipment.equipmentId,
                itemId = itemId
            )
            database.equipmentDao.insertCrossReff(crossref)
        }
    }


    suspend fun deleteItemFromEquipment(item: Item, player: Player) {
        withContext(Dispatchers.IO) {
            database.equipmentDao.removeCrossReff(
                item.itemId,
                player.character.equipment.equipmentId
            )
        }
    }

    suspend fun setNetwork(value: Boolean) {
        withContext(Dispatchers.IO) {
            _networkConnection.postValue(value)
        }
    }

    suspend fun updateSettings(settings: DatabaseSettings) {
        withContext(Dispatchers.IO) {
            database.settingsDao.insertAll(settings)
        }
    }

    suspend fun updatePlayer(player: Player) {
        withContext(Dispatchers.IO) {
            database.playerDao.updatePlayerNameAndPass(
                player.name,
                player.password,
                player.playerId
            )
        }
    }

    suspend fun insertRewardToPlayer(player: Player, item: Item?) {
        withContext(Dispatchers.IO) {
            _player.postValue(player)
            database.playerDao.updateCoins(player.coins, player.playerId)
            database.characterDao.updateExpFromCharacter(
                player.character.exp,
                player.character.characterId
            )
            updateStatusPoints(player)
            if (item != null) {
                database.inventoryDao.insertCrossReff(item.itemId, player.inventory.inventoryId)
            }
        }
    }

    suspend fun setProgressStages(player: Player) {
        withContext(Dispatchers.IO) {
            _player.postValue(player)
            database.playerDao.updateProgress(player.progress, player.playerId)

        }
    }

    suspend fun updateStatusPoints(player: Player) {
        withContext(Dispatchers.IO) {
            database.playerDao.updateStatus(
                player.statusPointsLeft,
                player.statusPointsDefence,
                player.statusPointsAttack,
                player.statusPointsStrength,
                player.statusPointsHitpoints,
                player.playerId
            )
            _player.postValue(player)
        }

    }

    suspend fun updateStatsPlayer(player: Player) {
        withContext(Dispatchers.IO) {
            val updateStats = DatabaseStats(
                statsId = player.character.stats.statsId,
                attack = player.character.stats.attack,
                strength = player.character.stats.strength,
                defence = player.character.stats.defence,
                lifepoints = player.character.stats.lifepoints
            )
            database.statsDao.insertAll(updateStats)
            _player.postValue(player)
        }
    }

    //Functions to Link Character , Stats, Equipment with Player with 1st Login ------------------------------------------------------------------------------------------------- REGISTER SECTION --------------------------------------------------------------------------------------------


    suspend fun makeCharacterForPlayer(
        dbPlayer: DatabasePlayer,
        character: DatabaseCharacter,
        statsId: String,
        equipmentId: String
    ): String {
        return withContext(Dispatchers.IO) {
            val characterId = UUID.randomUUID().toString()
            val characterForPlayer = DatabaseCharacter(
                characterId = characterId,
                exp = character.exp,
                image = character.image,
                equipmentId = equipmentId,
                isHero = character.isHero,
                name = character.name + characterId.subSequence(0, 7),
                statsId = statsId
            )
            _dbPlayer.postValue(
                DatabasePlayer(
                    playerId = dbPlayer.playerId,
                    name = dbPlayer.name,
                    password = dbPlayer.password,
                    characterId = characterId,
                    coins = dbPlayer.coins,
                    progress = dbPlayer.progress,
                    inventoryId = dbPlayer.inventoryId,
                    statusPointsLeft = dbPlayer.statusPointsLeft,
                    statusPointsAttack = dbPlayer.statusPointsAttack,
                    statusPointsDefence = dbPlayer.statusPointsDefence,
                    statusPointsStrength = dbPlayer.statusPointsStrength,
                    statusPointsHitpoints = dbPlayer.statusPointsHitpoints
                )
            )
            database.characterDao.insertAll(characterForPlayer)
            database.playerDao.updatePlayerCharacter(characterId, dbPlayer.playerId)
            return@withContext characterId
        }
    }

    suspend fun createStatsForCharacter(): String {
        return withContext(Dispatchers.IO) {
            val statsid = UUID.randomUUID().toString()
            database.statsDao.insertAll(
                DatabaseStats(
                    statsId = statsid,
                    attack = 1,
                    strength = 1,
                    defence = 1,
                    lifepoints = 10
                )
            )
            return@withContext statsid
        }

    }

    suspend fun makeEquipmentForPlayer(dbPlayer: DatabasePlayer): String {
        return withContext(Dispatchers.IO) {
            val equipmentId = UUID.randomUUID().toString()
            val dbEquipment = DatabaseEquipment(
                equipmentId,
                characterId = dbPlayer.characterId,
                name = dbPlayer.name!!
            )
            database.equipmentDao.insertAll(dbEquipment)
            return@withContext equipmentId
        }
    }

    suspend fun updateEquipmentForPlayerCharacter(characterId: String, equipmentId: String) {
        withContext(Dispatchers.IO) {
            database.equipmentDao.updateCharacterIdInEquipment(characterId, equipmentId)
        }
    }

    suspend fun stopRegistering() {
        withContext(Dispatchers.IO) {
            _registering.postValue(false)
        }
    }
//Fetching  From Api, mapping to DatabaseModel -------------------------------------------------------------------------API SECTION -----------------------------------------------------------------------------------------------------------


    suspend fun refreshBackgrounds() {
        withContext(Dispatchers.IO) {
            val backgroundList = SitorApi.sitorApiService.getBackgrounds().await()
            database.backgroundDao.insertAll(
                *backgroundList.toTypedArray()
            )
            Timber.i("Inserted all Backgrounds to db")
        }
    }

    suspend fun refreshCategories() {

        withContext(Dispatchers.IO) {
            val categorylist = SitorApi.sitorApiService.getCategories().await()
            database.categoryDao.insertAll(*categorylist.toTypedArray())
            Timber.i("Inserted all categories to db")
        }

    }

    //This method is written if all data is kept in the external database to fetch them
    suspend fun refreshPlayers() {

        withContext(Dispatchers.IO) {
            val playerlist = SitorApi.sitorApiService.getPlayers().await()
            database.playerDao.insertAll(*playerlist.toTypedArray())
            Timber.i("Inserted all players to db")
        }
    }

    suspend fun refreshCategoryTypes() {
        withContext(Dispatchers.IO) {
            val categoryTypelist = SitorApi.sitorApiService.getCategoryTypes().await()
            database.categoryDao.insertCrossReff(*categoryTypelist.toTypedArray())
            Timber.i("Inserted all categoryType to db")
        }

    }

    suspend fun refreshCharacterList() {
        withContext(Dispatchers.IO) {
            val characterList = SitorApi.sitorApiService.getCharacters().await()
            database.characterDao.insertAll(*characterList.toTypedArray())
            Timber.i("Inserted all characters to db")
        }

    }

    suspend fun refreshEffects() {
        withContext(Dispatchers.IO) {
            val effectList = SitorApi.sitorApiService.getEffects().await()
            database.effectDao.insertAll(*effectList.toTypedArray())
            Timber.i("Inserted all effects to db")
        }

    }

    suspend fun refreshEffectLists() {
        withContext(Dispatchers.IO) {
            val effectListSet = SitorApi.sitorApiService.getEffectLists().await()
            database.itemDao.insertCrossReffEffect(*effectListSet.toTypedArray())
            Timber.i("Inserted all itemEffectCrossRefs to db")
        }

    }

    suspend fun refreshEquipmentList() {
        withContext(Dispatchers.IO) {
            val equipmentList = SitorApi.sitorApiService.getEquipments().await()
            database.equipmentDao.insertAll(*equipmentList.toTypedArray())
            Timber.i("Inserted all equipments to db")
        }

    }

    suspend fun refreshEquipmentItemsList() {
        withContext(Dispatchers.IO) {
            val equipmentItemsList = SitorApi.sitorApiService.getEquipmentItems().await()
            database.equipmentDao.insertCrossReff(*equipmentItemsList.toTypedArray())
            Timber.i("Inserted all equipmentItems to db")
        }

    }

    //This method is written if all data is kept in the external database to fetch them
    suspend fun refreshInventoryList() {
        withContext(Dispatchers.IO) {
            val inventoryList = SitorApi.sitorApiService.getInventory().await()
            database.inventoryDao.insertAll(*inventoryList.toTypedArray())
            Timber.i("Inserted all inventories to db")
        }
    }

    //This method is written if all data is kept in the external database to fetch them
    suspend fun refreshInventoryItemsList() {
        withContext(Dispatchers.IO) {
            val inventoryItemsList = SitorApi.sitorApiService.getInventoryItemsSet().await()
            database.inventoryDao.insertAllCrossReffs(*inventoryItemsList.toTypedArray())
            Timber.i("Inserted all inventoryItems to db")
        }

    }

    suspend fun refreshItems() {

        withContext(Dispatchers.IO) {
            val itemList = SitorApi.sitorApiService.getItems().await()
            database.itemDao.insertAll(*itemList.toTypedArray())
            Timber.i("Inserted all items to db")
        }
    }

    suspend fun refreshRewards() {
        withContext(Dispatchers.IO) {
            val rewardList = SitorApi.sitorApiService.getRewards().await()
            database.rewardDao.insertAll(*rewardList.toTypedArray())
            Timber.i("Inserted all rewards to db")
        }
    }

    suspend fun refreshShops() {

        withContext(Dispatchers.IO) {
            val shopList = SitorApi.sitorApiService.getShops().await()
            database.shopDao.insertAll(*shopList.toTypedArray())
            Timber.i("Inserted all shops to db")
        }
    }

    suspend fun refreshStage() {

        withContext(Dispatchers.IO) {
            val stageList = SitorApi.sitorApiService.getStages().await()
            database.stageDao.insertAll(*stageList.toTypedArray())
            Timber.i("Inserted all shops to db")
        }
    }

    suspend fun refreshStats() {

        withContext(Dispatchers.IO) {
            val statsList = SitorApi.sitorApiService.getStats().await()
            database.statsDao.insertAll(*statsList.toTypedArray())
            Timber.i("Inserted all Stats to db")
        }
    }

    suspend fun refreshStocks() {

        withContext(Dispatchers.IO) {
            val stockList = SitorApi.sitorApiService.getStocks().await()
            database.shopDao.insertCrossReff(*stockList.toTypedArray())
            Timber.i("Inserted all stocks to db")
        }
    }

    suspend fun refreshTypes() {

        withContext(Dispatchers.IO) {
            val typeList = SitorApi.sitorApiService.getTypes().await()
            database.typeDao.insertAll(*typeList.toTypedArray())
            Timber.i("Inserted all types to db")
        }
    }

    suspend fun refreshTypeItems() {

        withContext(Dispatchers.IO) {
            val typeItemList = SitorApi.sitorApiService.getTypeItems().await()
            database.typeDao.insertCrossReff(*typeItemList.toTypedArray())
            Timber.i("Inserted all typeItems to db")
        }
    }

}