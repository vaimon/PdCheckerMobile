package ru.mmcs.pdcheckermobile.ui.login.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.android.volley.RequestQueue
import kotlinx.coroutines.launch
import ru.mmcs.pdcheckermobile.data.LoginRepository
import ru.mmcs.pdcheckermobile.utils.Result

import ru.mmcs.pdcheckermobile.R
import ru.mmcs.pdcheckermobile.data.models.User
import ru.mmcs.pdcheckermobile.data.services.AuthenticationService
import ru.mmcs.pdcheckermobile.data.services.SharedPreferencesService

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _isRegistrationMode = MutableLiveData<Boolean>(false)
    val isRegistrationMode: LiveData<Boolean> = _isRegistrationMode

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun onBtnActionPressed(username: String, password: String, name: String, role: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result : Result<User> = if(isRegistrationMode.value!!){
                loginRepository.register(username, password, name, role)
            }else{
                loginRepository.login(username, password)
            }

            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = true, role = result.data.role)
            } else {
                Log.d("Error",result.toString())
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
            _isLoading.value = false
        }
    }

    fun toggleMode(){
        _isRegistrationMode.value = !_isRegistrationMode.value!!
    }

    fun loginDataChanged(username: String, password: String) {
        if (username.isBlank()) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }

    data class LoginFormState(
        val usernameError: Int? = null,
        val passwordError: Int? = null,
        val isDataValid: Boolean = false
    )

    data class LoginResult(
        val success: Boolean = false,
        val role: String? = null,
        val error: Int? = null
    )

    class LoginViewModelFactory(val queue: RequestQueue) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(
                    loginRepository = LoginRepository.createInstance(
                        authService = AuthenticationService(queue),
                        spService = SharedPreferencesService()
                    )
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}