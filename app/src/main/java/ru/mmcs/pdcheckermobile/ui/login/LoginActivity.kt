package ru.mmcs.pdcheckermobile.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.toolbox.Volley
import ru.mmcs.pdcheckermobile.databinding.ActivityLoginBinding

import ru.mmcs.pdcheckermobile.ui.login.viewmodels.LoginViewModel
import ru.mmcs.pdcheckermobile.ui.main.MainActivity
import ru.mmcs.pdcheckermobile.utils.Extensions.afterTextChanged

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this,
            LoginViewModel.LoginViewModelFactory(Volley.newRequestQueue(this))
        ).get(LoginViewModel::class.java)

        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
        val role = loginViewModel.checkUserRole()
        if(role != null){
            endAuthorization(role)
        }

        binding.modeToggle.setOnClickListener {
            loginViewModel.toggleMode()
        }

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success) {
                endAuthorization(loginResult.role)
            }
        })

        binding.username.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }

            binding.login.setOnClickListener {
                loginViewModel.onBtnActionPressed(binding.username.text.toString(), binding.password.text.toString(), binding.name.text.toString(), if (binding.role.isChecked) "judge" else "student")
            }
        }
    }

    fun endAuthorization(role: String?){
        startActivity(Intent(this, MainActivity::class.java).putExtra("role", role))
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}