package be.vives.gamesitor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import be.vives.gamesitor.database.GameSitorDatabase
import be.vives.gamesitor.database.dbRelationships.crossRefs.ItemEffectCrossRef
import be.vives.gamesitor.database.entities.DatabasePlayer
import be.vives.gamesitor.database.entities.DatabaseStage
import be.vives.gamesitor.mappingUtils.asDomainModelBackground
import be.vives.gamesitor.models.Background
import be.vives.gamesitor.models.Item
import be.vives.gamesitor.network.SitorApi
import be.vives.gamesitor.user.login.data.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import timber.log.Timber


class Repository(private val database: GameSitorDatabase) {

    private val loginRepository = LoginRepository(database)

    // Fetching from Database , mapping to DomainModel
    val backgrounds: LiveData<List<Background>> =
        Transformations.map(database.backgroundDao.getBackgrounds()) {
            it.asDomainModelBackground()
        }
    private val _player = database.playerDao.getPlayerByUserNameAndPass("varsium","password")
    val player: LiveData<DatabasePlayer> get() = _player
    val background: LiveData<List<Background>> = Transformations.map(backgrounds) {
        it.filter { background -> background.backgroundId == 1 }
    }
    private val _itemEffectCrossRef: LiveData<List<ItemEffectCrossRef>> =
        database.itemDao.getEffectListSet()
    val itemEffectCrossRef: LiveData<List<ItemEffectCrossRef>> get() = _itemEffectCrossRef

    val items: LiveData<List<Item>> = database.itemDao.getAllItemsWithEffects()

    fun getChosenItem(itemId: Int): LiveData<Item> {
        return database.itemDao.getItemsWithEffects(itemId)
    }



    //Still needs checking
    suspend fun insertItemToInventory(itemId: Int, playerId: Int) {
        withContext(Dispatchers.IO) {
            database.inventoryDao.insertCrossReff(itemId, playerId)
        }

    }


    fun getStage(stageId: Int): LiveData<DatabaseStage> {
        return database.stageDao.getStageById(stageId)
    }


    //Fetching from Database
    suspend fun getBackgrounds() {
        withContext(Dispatchers.IO) {
            val backgroundList = database.backgroundDao.getBackgrounds()
        }
    }
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

    suspend fun refreshInventoryList() {
        withContext(Dispatchers.IO) {
            val inventoryList = SitorApi.sitorApiService.getInventory().await()
            database.inventoryDao.insertAll(*inventoryList.toTypedArray())
            Timber.i("Inserted all inventories to db")
        }
    }

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