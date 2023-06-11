package ru.mmcs.pdcheckermobile.data

import org.json.JSONObject
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
        return fetchUser(authService.login(username, password))
    }

    suspend fun register(username: String, password: String, name: String, role: String) : Result<User> {
        return fetchUser(authService.register(username, password, name, role))
    }

    suspend fun fetchUser(result: Result<JSONObject>): Result<User>{
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

    // Dagger, pls come and help me
    companion object{
        private lateinit var repository: LoginRepository

        fun createInstance(authService: AuthenticationService, spService: SharedPreferencesService) : LoginRepository{
            repository = LoginRepository(authService, spService)
            return repository
        }

        fun getInstance(): LoginRepository{
            return repository
        }
    }
}