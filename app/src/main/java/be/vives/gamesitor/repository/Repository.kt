package be.vives.gamesitor.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import be.vives.gamesitor.MappingUtils.*
import be.vives.gamesitor.database.GameSitorDatabase
import be.vives.gamesitor.database.dbRelationships.CrossRefs.ItemEffectCrossRef
import be.vives.gamesitor.domain.models.Background
import be.vives.gamesitor.domain.models.ItemWithEffect
import be.vives.gamesitor.network.SitorApi
import kotlinx.coroutines.*
import timber.log.Timber

enum class SitorApiStatus { LOADING, ERROR, DONE }

class Repository(private val database: GameSitorDatabase) {
    //Checking Status of Api Connection
    private val _status = MutableLiveData<SitorApiStatus>()
    val status: LiveData<SitorApiStatus>
        get() = _status

    // Fetching from Database , mapping to DomainModel
    val backgrounds: LiveData<List<Background>> =
        Transformations.map(database.backgroundDao.getBackgrounds()) {
            it.asDomainModelBackground()
        }
//    val categories: LiveData<List<Category>> =
//        Transformations.map(database.categoryDao.getCategories()) {
//            it.asDomainModel()
//        }

    val items: LiveData<List<ItemWithEffect>> =
        Transformations.map(database.itemDao.getItems()) {
            it
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
            _status.postValue(SitorApiStatus.LOADING)
            val categorylist = SitorApi.sitorApiService.getCategories().await()
            _status.postValue(SitorApiStatus.DONE)
            database.categoryDao.insertAll(*categorylist.toTypedArray())
            Timber.i("Inserted all categories to db")
        }

    }

    suspend fun refreshCategoryTypes() {
        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val categoryTypelist = SitorApi.sitorApiService.getCategoryTypes().await()
            _status.postValue(SitorApiStatus.DONE)
            database.categoryDao.insertCrossReff(*categoryTypelist.toTypedArray())
            Timber.i("Inserted all categoryType to db")
        }

    }

    suspend fun refreshEffects() {
        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val effectList = SitorApi.sitorApiService.getEffects().await()
            _status.postValue(SitorApiStatus.DONE)
            database.effectDao.insertAll(*effectList.toTypedArray())
            Timber.i("Inserted all effects to db")
        }

    }

    suspend fun refreshEffectLists() {
        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val effectListSet = SitorApi.sitorApiService.getEffectLists().await()
            _status.postValue(SitorApiStatus.DONE)
            database.itemDao.insertCrossReffEffect(*effectListSet.toTypedArray())
            Timber.i("Inserted all itemEffectCrossRefs to db")
        }

    }

    suspend fun characterList() {
        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val characterList = SitorApi.sitorApiService.getCharacters().await()
            _status.postValue(SitorApiStatus.DONE)
            database.characterDao.insertAll(*characterList.toTypedArray())
            Timber.i("Inserted all characters to db")
        }

    }

    suspend fun equipmentList() {
        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val equipmentList = SitorApi.sitorApiService.getEquipments().await()
            _status.postValue(SitorApiStatus.DONE)
            database.equipmentDao.insertAll(*equipmentList.toTypedArray())
            Timber.i("Inserted all equipments to db")
        }

    }

    suspend fun equipmentItemsList() {
        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val equipmentItemsList = SitorApi.sitorApiService.getEquipmentItems().await()
            _status.postValue(SitorApiStatus.DONE)
            database.equipmentDao.insertCrossReff(*equipmentItemsList.toTypedArray())
            Timber.i("Inserted all equipmentItems to db")
        }

    }

    suspend fun inventoryList() {
        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val inventoryList = SitorApi.sitorApiService.getInventory().await()
            _status.postValue(SitorApiStatus.DONE)
            database.inventoryDao.insertAll(*inventoryList.toTypedArray())
            Timber.i("Inserted all inventories to db")
        }

    }

    suspend fun inventoryItemsList() {
        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val inventoryItemsList = SitorApi.sitorApiService.getInventoryItemsSet().await()
            _status.postValue(SitorApiStatus.DONE)
            database.inventoryDao.insertCrossReff(*inventoryItemsList.toTypedArray())
            Timber.i("Inserted all inventoryItems to db")
        }

    }

    suspend fun refreshItems() {

        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val itemList = SitorApi.sitorApiService.getItems().await()
            _status.postValue(SitorApiStatus.DONE)
            database.itemDao.insertAll(*itemList.toTypedArray())
            Timber.i("Inserted all items to db")
        }
    }

    suspend fun refreshRewards() {

        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val rewardList = SitorApi.sitorApiService.getRewards().await()
            _status.postValue(SitorApiStatus.DONE)
            database.rewardDao.insertAll(*rewardList.toTypedArray())
            Timber.i("Inserted all rewards to db")
        }
    }

    suspend fun refreshShops() {

        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val shopList = SitorApi.sitorApiService.getShops().await()
            _status.postValue(SitorApiStatus.DONE)
            database.shopDao.insertAll(*shopList.toTypedArray())
            Timber.i("Inserted all shops to db")
        }
    }

    suspend fun refreshStage() {

        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val stageList = SitorApi.sitorApiService.getStages().await()
            _status.postValue(SitorApiStatus.DONE)
            database.stageDao.insertAll(*stageList.toTypedArray())
            Timber.i("Inserted all shops to db")
        }
    }

    suspend fun refreshStats() {

        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val statsList = SitorApi.sitorApiService.getStats().await()
            _status.postValue(SitorApiStatus.DONE)
            database.statsDao.insertAll(*statsList.toTypedArray())
            Timber.i("Inserted all Stats to db")
        }
    }

    suspend fun refreshStocks() {

        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val stockList = SitorApi.sitorApiService.getStocks().await()
            _status.postValue(SitorApiStatus.DONE)
            database.shopDao.insertCrossReff(*stockList.toTypedArray())
            Timber.i("Inserted all stocks to db")
        }
    }

    suspend fun refreshTypes() {

        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val typeList = SitorApi.sitorApiService.getTypes().await()
            _status.postValue(SitorApiStatus.DONE)
            database.typeDao.insertAll(*typeList.toTypedArray())
            Timber.i("Inserted all types to db")
        }
    }

    suspend fun refreshTypeItems() {

        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val typeItemList = SitorApi.sitorApiService.getTypeItems().await()
            _status.postValue(SitorApiStatus.DONE)
            database.typeDao.insertCrossReff(*typeItemList.toTypedArray())
            Timber.i("Inserted all typeItems to db")
        }
    }

}