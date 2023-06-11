package ru.mmcs.pdcheckermobile.data

import ru.mmcs.pdcheckermobile.data.models.User
import ru.mmcs.pdcheckermobile.data.services.AuthenticationService
import ru.mmcs.pdcheckermobile.data.services.SharedPreferencesService
import ru.mmcs.pdcheckermobile.utils.Result


class LoginRepository(val authService: AuthenticationService, val spService: SharedPreferencesService) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        // TODO
    }

    suspend fun login(username: String, password: String): Result<User> {
        val result = authService.login(username, password)

        if (result is Result.Success) {
            spService.saveJwtToken(result.data.getString("access_token"))

            val userResult = authService.getUserInfo(result.data.getString("username"))
            if(userResult is Result.Success){
                setLoggedInUser(userResult.data)
            }
            return userResult
        }
        return result as Result.Error
    }

    private fun setLoggedInUser(loggedInUser: User) {
        this.user = loggedInUser
    }
}