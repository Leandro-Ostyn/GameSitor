package be.vives.gamesitor.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.vives.gamesitor.network.entities.Category
import be.vives.gamesitor.network.entities.Stats
import be.vives.gamesitor.network.SitorApi
import be.vives.gamesitor.network.entities.Background
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import timber.log.Timber

enum class SitorApiStatus { LOADING, ERROR, DONE }

class RepositoryExample {
    private var repositoryJob = Job()
    private val coroutineScope = CoroutineScope(repositoryJob + Dispatchers.Main)


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
    val backgrounds: LiveData<List<Background>>
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


    fun getStats(statsId: Int) {

        coroutineScope.launch {
            val getStatsDeffered = SitorApi.sitorApiService.getStats(statsId)
            try {
                _status.value = SitorApiStatus.LOADING
                Timber.i(status.value.toString())
                val listResult = getStatsDeffered.await()
                _status.value = SitorApiStatus.DONE
                Timber.i(listResult.lifepoints.toString())
                Timber.i(status.value.toString())

            } catch (t: Throwable) {
                _status.value = SitorApiStatus.ERROR
                _categories.value = ArrayList()
            }
        }
    }

    fun cancelJobs() {
        repositoryJob.cancel()
    }
}