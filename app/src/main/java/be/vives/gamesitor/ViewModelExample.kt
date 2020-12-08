package be.vives.gamesitor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import be.vives.gamesitor.network.entities.Stats
import be.vives.gamesitor.network.entities.Type
import be.vives.gamesitor.database.repositories.RepositoryExample
import timber.log.Timber

class ViewModelExample : ViewModel() {

     val repositoryExample = RepositoryExample()
    private val _typeId: MutableLiveData<Int> = MutableLiveData()


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
//    val stats: LiveData<Stats> = Transformations.switchMap(_statsId) {
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
        repositoryExample.cancelJobs()
    }
}