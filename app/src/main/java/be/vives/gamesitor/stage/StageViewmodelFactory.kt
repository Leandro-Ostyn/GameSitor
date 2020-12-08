package be.vives.gamesitor.stage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.vives.gamesitor.database.repositories.RepositoryExample

class StageViewmodelFactory (
    private val dataSource: RepositoryExample) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ViewModel::class.java)) {
           //     return StageViewmodel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}