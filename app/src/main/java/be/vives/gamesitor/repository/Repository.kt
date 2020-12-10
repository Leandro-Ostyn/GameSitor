package be.vives.gamesitor.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import be.vives.gamesitor.MappingUtils.asDataBaseModel
import be.vives.gamesitor.MappingUtils.asDomainModel
import be.vives.gamesitor.database.GameSitorDatabase
import be.vives.gamesitor.domain.models.Background
import be.vives.gamesitor.domain.models.Category
import be.vives.gamesitor.domain.models.Item
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
            it.asDomainModel()
        }
//    val categories: LiveData<List<Category>> =
//        Transformations.map(database.categoryDao.getCategories()) {
//            it.asDomainModel()
//        }

    val items: LiveData<List<Item>> =
        Transformations.map(database.itemDao.getItems()) {
            it.asDomainModel()
        }



    //Fetching  From Api, mapping to DatabaseModel

    // Background Fetching from Api, Mapping to DatabaseModel
    suspend fun refreshBackgrounds() {
        withContext(Dispatchers.IO) {
            val backgroundList = SitorApi.sitorApiService.getBackgrounds().await()
            database.backgroundDao.insertAll(*backgroundList.asDataBaseModel().toTypedArray())
            Timber.i("Inserted all Backgrounds to db")
        }
    }

    suspend fun refreshCategories() {
        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val categorylist = SitorApi.sitorApiService.getCategories().await()
            _status.postValue(SitorApiStatus.DONE)
            //   database.categoryDao.insertAll(*categorylist.asDataBaseModel())
            Timber.i("Inserted all categories to db")
        }

    }

    suspend fun refreshStats() {

        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val statsList = SitorApi.sitorApiService.getStats().await()
            _status.postValue(SitorApiStatus.DONE)
            database.statsDao.insertAll(*statsList.asDataBaseModel().toTypedArray())
            Timber.i("Inserted all Stats to db")
        }
    }

    suspend fun refreshItems() {

        withContext(Dispatchers.IO) {
            _status.postValue(SitorApiStatus.LOADING)
            val itemList = SitorApi.sitorApiService.getItems().await()
            _status.postValue(SitorApiStatus.DONE)
            database.itemDao.insertAll(*itemList.asDataBaseModel().toTypedArray())
            Timber.i("Inserted all Stats to db")
        }
    }

}