package be.vives.gamesitor

import android.app.Application
import androidx.lifecycle.*
import be.vives.gamesitor.database.getDatabase

import be.vives.gamesitor.database.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


class ViewModelExample(application: Application) : AndroidViewModel(application) {
private val database = getDatabase(application)
     private val repository = Repository(database)
    private val _typeId: MutableLiveData<Int> = MutableLiveData()

init {
    viewModelScope.launch {
        try {
            repository.refreshBackgrounds()
            Timber.i("Worked")
        } catch (e: Exception){
            Timber.i("I tried to make it work "+ e.message!! )
        }
    }
}

    val backgrounds = repository.backgrounds
    fun setTypeId(typeId: Int) {
        val update = typeId
        if (_typeId.value != update) {
            _typeId.value = update
        } else {
            return
        }
    }
fun checkCategories (){

}

    private val _statsId: MutableLiveData<Int> = MutableLiveData()
//    val stats: LiveData<DatabaseStats> = Transformations.switchMap(_statsId) {
//        Timber.i("transformed")
//        repositoryExample.getStats(it)
//    }

    fun setStatsId(statsId: Int) {
        val update = statsId
        if (_statsId.value != update) {
            _statsId.value = update
        } else {
            return
        }
    }

    fun cancelJobs() {
        repository.cancelJobs()
    }

}