package be.vives.gamesitor.login

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import be.vives.gamesitor.R
import be.vives.gamesitor.constants.getDatabase
import be.vives.gamesitor.constants.getRepository
import be.vives.gamesitor.database.entities.DatabaseInventory
import be.vives.gamesitor.database.entities.DatabasePlayer

import be.vives.gamesitor.models.loginModels.LoggedInUserView
import be.vives.gamesitor.models.loginModels.LoginFormState
import be.vives.gamesitor.models.loginModels.LoginResult
import be.vives.gamesitor.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val loginRepository = LoginRepository(database)
    private val repository = getRepository(database)

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _loggedInPlayer = MutableLiveData<DatabasePlayer>()
    val loggedInPlayer: LiveData<DatabasePlayer> get() = _loggedInPlayer

    fun login(player: DatabasePlayer) {
        _loggedInPlayer.postValue(player)
        _loginResult.postValue(LoginResult(LoggedInUserView(player.name!!)))
    }


    fun getPlayer(name: String): LiveData<DatabasePlayer> {
        return repository.getPlayer(name)

    }

    fun register(username: String, password: String, inventoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.register(username, password, inventoryId)
        }
    }

    fun setRegistering(){
       viewModelScope.launch(Dispatchers.IO) {
           repository.setRegistering()
       }
   }

    fun setPlayerForGame(databasePlayer: DatabasePlayer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setDbPlayer(databasePlayer)
        }
    }

    fun prepareInventoryForPlayer(): MutableLiveData<List<DatabaseInventory>> {
        val inventoryList = MutableLiveData<List<DatabaseInventory>>()
        viewModelScope.launch(Dispatchers.IO) {
            inventoryList.postValue(loginRepository.getInventories().value)
        }
        return inventoryList
    }

    fun createInventoryForPlayer(): MutableLiveData<String> {
        val inventoryId = MutableLiveData<String>()
        viewModelScope.launch(Dispatchers.IO) {
            inventoryId.postValue(loginRepository.createInventory())
        }
        return inventoryId
    }





    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }


}