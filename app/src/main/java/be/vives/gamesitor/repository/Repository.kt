package be.vives.gamesitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.vives.gamesitor.constants.*
import be.vives.gamesitor.database.GameSitorDatabase
import be.vives.gamesitor.database.dbRelationships.crossRefs.EquipmentItemsCrossRef
import be.vives.gamesitor.database.dbRelationships.crossRefs.InventoryItemsCrossRef
import be.vives.gamesitor.database.dbRelationships.crossRefs.TypeItemCrossRef
import be.vives.gamesitor.database.entities.*
import be.vives.gamesitor.models.*
import be.vives.gamesitor.network.SitorApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


class Repository(private val database: GameSitorDatabase) {
    private val _networkConnection = MutableLiveData<Boolean>()
    val networkConnection: LiveData<Boolean> get() = _networkConnection
    private val _chosenItem = MutableLiveData<Item>()
    val chosenItem: LiveData<Item> get() = _chosenItem
    private val _inventoryItems = MutableLiveData<List<Item>>()
    val inventoryItems: LiveData<List<Item>> get() = _inventoryItems
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
    private val _equippedItems = MutableLiveData<List<Item>>()
    val equippedItems: LiveData<List<Item>> get() = _equippedItems
    private val _characterFromPlayer = MutableLiveData<Character>()
    val characterFromPlayer: LiveData<Character> get() = _characterFromPlayer

    private val _dbPlayer = MutableLiveData<DatabasePlayer>()
    val dbPlayer: LiveData<DatabasePlayer> get() = _dbPlayer


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


    //Everything for Loading screen to Fill All variables.


    //Getters From Db
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
        _chosenItem.postValue(database.itemDao.getItemsWithEffects(itemId).value)
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
    //Setters to map from db to Domain Models.

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


    //Methods to set everything for Player
    suspend fun setDbPlayer(databasePlayer: DatabasePlayer) {
        withContext(Dispatchers.IO) { _dbPlayer.postValue(databasePlayer) }
    }

    fun setPlayerItems(items: List<Item>) {
        _inventoryItems.postValue(items)
    }

    fun returnChosenEquipmentList(type: String): MutableLiveData<List<Item>> {
        return when (type) {
            WEAPON -> _playerWeaponList
            H2WEAPON -> _player2HWeaponList
            HELMET -> _playerHelmetList
            BODY -> _playerBodyList
            LEGS -> _playerLegsList
            SHIELD -> _playerShieldList
            else -> {
                _playerBootsList
            }
        }

    }


//everything for inventory

    fun getInventoryCrossReff(inventoryId: Int): LiveData<List<InventoryItemsCrossRef>> {
        return database.inventoryDao.getCrossReffInventoryItems(inventoryId)

    }

    fun getFilteredItems(list: List<Int>): LiveData<List<Item>> {
        return database.itemDao.getFilteredItemsWithEffects(list)
    }

    fun setNetwork(value: Boolean) {
        _networkConnection.value = value
    }

    fun setStageList(stageList: List<Stage>) {
        _stages.postValue(stageList)
    }

    suspend fun insertItemToInventory(itemId: Int, inventoryId: Int) {
        withContext(Dispatchers.IO) {
            database.inventoryDao.insertCrossReff(itemId, inventoryId)
        }
    }

    suspend fun deleteItemFromInventory(inventoryId: Int, itemId: Int) {
        withContext(Dispatchers.IO) {
            database.inventoryDao.deleteCrossReff(inventoryId, itemId)
        }
    }


    //Everything For Equipment
    fun getTypeIdFromName(type: String): LiveData<DatabaseType> {
        return database.typeDao.getTypeByName(type)
    }

    fun getItemIdsInType(typeId: Int): LiveData<List<TypeItemCrossRef>> {
        return database.typeDao.getItemsByCrossRefWithType(typeId)
    }

    fun getEquipmentId(characterId: Int): LiveData<DatabaseEquipment> {
        return database.equipmentDao.getEquipmentFromCharacter(characterId)
    }

    fun setFilteredListForEquipment(itemList: List<Item>, type: String) {
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

    fun getCrossRefEquipmentItems(equipmentId: Int): LiveData<List<EquipmentItemsCrossRef>> {
        return database.equipmentDao.getItemsFromEquipment(equipmentId)

    }

    suspend fun setEquippedItems(equippedItems: List<Item>) {
        withContext(Dispatchers.IO) {
            _equippedItems.postValue(equippedItems)
        }
    }

    suspend fun equipToPlayer(equipmentId: Int, itemId: Int) {
        val crossref = EquipmentItemsCrossRef(0, equipmentId = equipmentId, itemId = itemId)
        withContext(Dispatchers.IO) {
            database.equipmentDao.insertCrossReff(crossref)
        }
    }

//everything for Stage

    fun getDatabaseStage(stageId: Int): LiveData<DatabaseStage> {
        return database.stageDao.getStageById(stageId)
    }

    fun getDatabaseStages(): LiveData<List<DatabaseStage>> {
        return database.stageDao.getStages()
    }


    fun getBackgroundById(backgroundId: Int): LiveData<DatabaseBackground> {
        return database.backgroundDao.getBackgroundById(backgroundId)
    }

    fun getRewardFromStage(rewardId: Int): LiveData<DatabaseReward> {
        return database.rewardDao.getReward(rewardId)
    }

    fun getDatabaseCharacter(characterId: Int): LiveData<DatabaseCharacter> {
        return database.characterDao.getCharacterById(characterId)
    }

    fun getStatsById(statsId: Int): LiveData<DatabaseStats> {
        return database.statsDao.getStats(statsId)
    }




//-------------------------------------------------------------------------API SECTION -----------------------------------------------------------------------------------------------------------
//Fetching  From Api, mapping to DatabaseModel

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