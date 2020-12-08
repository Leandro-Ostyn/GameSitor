package be.vives.gamesitor.database.repositories

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import be.vives.gamesitor.database.GameSitorDatabase
import be.vives.gamesitor.database.entities.asDataBaseModel
import be.vives.gamesitor.database.entities.asDomainModel
import be.vives.gamesitor.domain.models.Category
import be.vives.gamesitor.network.SitorApi
import be.vives.gamesitor.domain.models.Background
import kotlinx.coroutines.*
import timber.log.Timber

enum class SitorApiStatus { LOADING, ERROR, DONE }

class Repository(private val database: GameSitorDatabase) {
    private var repositoryJob = Job()
    private val coroutineScope = CoroutineScope(repositoryJob + Dispatchers.Main)

    val backgrounds: LiveData<List<Background>> = Transformations.map(database.backgroundDao.getBackgrounds()){
        it.asDomainModel()
    }
suspend fun refreshBackgrounds(){
    withContext(Dispatchers.IO){
        val backgroundList = SitorApi.sitorApiService.getBackgrounds().await()
        database.backgroundDao.insertAll(*backgroundList.asDataBaseModel())
    }
}

    private val _categories = MutableLiveData<List<Category>>()
    val gategories: LiveData<List<Category>>
        get() = _categories

    private val _status = MutableLiveData<SitorApiStatus>()
    val status: LiveData<SitorApiStatus>
        get() = _status

    fun getCategories() {
        coroutineScope.launch {
            val getCategoriesDeferred = SitorApi.sitorApiService.getCategories()
            try {
                _status.value = SitorApiStatus.LOADING
                Timber.i(status.value.toString())
                val listResult = getCategoriesDeferred.await()
                _status.value = SitorApiStatus.DONE
                Timber.i(listResult.size.toString())
                Timber.i(status.value.toString())

                if (listResult.isNotEmpty()) {
                    _categories.value = listResult
                }

            } catch (t: Throwable) {
                _status.value = SitorApiStatus.ERROR
                _categories.value = ArrayList()
            }
        }

    }

    private val _backgrounds = MutableLiveData<List<Background>>()
    val backgroundz: LiveData<List<Background>>
        get() = _backgrounds

    fun getBackgrounds() {
        coroutineScope.launch {
            val getBackgroundsDeferred = SitorApi.sitorApiService.getBackgrounds()
            try {
                _status.value = SitorApiStatus.LOADING
                Timber.i(status.value.toString())
                val listResult = getBackgroundsDeferred.await()
                _status.value = SitorApiStatus.DONE
                Timber.i(listResult.size.toString())
                Timber.i(status.value.toString())

                if (listResult.isNotEmpty()) {
                    _backgrounds.value = listResult
                }

            } catch (t: Throwable) {
                _status.value = SitorApiStatus.ERROR
                _categories.value = ArrayList()
            }
        }

    }


//    fun getStats(statsId: Int) {
//
//        coroutineScope.launch {
//            val getStatsDeffered = SitorApi.sitorApiService.getStats(statsId)
//            try {
//                _status.value = SitorApiStatus.LOADING
//                Timber.i(status.value.toString())
//                val listResult = getStatsDeffered.await()
//                _status.value = SitorApiStatus.DONE
//                Timber.i(listResult.lifepoints.toString())
//                Timber.i(status.value.toString())
//
//            } catch (t: Throwable) {
//                _status.value = SitorApiStatus.ERROR
//                _categories.value = ArrayList()
//            }
//        }
//    }

    fun cancelJobs() {
        repositoryJob.cancel()
    }
}