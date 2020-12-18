package be.vives.gamesitor.user.login.data

import be.vives.gamesitor.database.GameSitorDatabase
import be.vives.gamesitor.user.login.data.model.LoggedInUser
import java.io.IOException


class LoginRepository(private val database: GameSitorDatabase) {

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

//    fun logout() {
//        user = null
//            .logout()
//    }
//
//    fun loginresult(): Result<LoggedInUser> {
//        val result = dataSource.login(username, password)
//
//        if (result is Result.Success) {
//            setLoggedInUser(result.data)
//        }
//
//        return result
//    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }


//    private fun login(username: String, password: String): Result<LoggedInUser> {
//        try {
//            val player = database.playerDao.getPlayerByUserName(username)
//            if (player.value != null) {
//
//                return Result.Success(player)
//            }
//        } catch (e: Throwable) {
//            return Result.Error(IOException("Error logging in", e))
//        }
//    }
}