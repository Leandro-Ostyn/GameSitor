package be.vives.gamesitor.user.login.ui.login

import android.app.Application

import android.util.Patterns
import androidx.lifecycle.*
import be.vives.gamesitor.R
import be.vives.gamesitor.database.entities.DatabasePlayer
import be.vives.gamesitor.database.getDatabase
import be.vives.gamesitor.user.login.data.LoginRepository
import kotlinx.coroutines.launch


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val loginRepository = LoginRepository(database)

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


    fun getplayer(name: String, password: String): LiveData<DatabasePlayer> {
       return loginRepository.getplayer(name, password)
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            loginRepository.register(username, password)
        }
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