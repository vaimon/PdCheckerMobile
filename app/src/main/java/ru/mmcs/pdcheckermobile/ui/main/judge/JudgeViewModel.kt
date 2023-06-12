package ru.mmcs.pdcheckermobile.ui.main.judge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import ru.mmcs.pdcheckermobile.data.LoginRepository
import ru.mmcs.pdcheckermobile.data.ProjectRepository
import ru.mmcs.pdcheckermobile.data.services.ApiService
import ru.mmcs.pdcheckermobile.data.services.AuthenticationService
import ru.mmcs.pdcheckermobile.data.services.SharedPreferencesService
import ru.mmcs.pdcheckermobile.ui.login.viewmodels.LoginViewModel

class JudgeViewModel(val repository: ProjectRepository) : ViewModel() {



    class JudgeViewModelFactory(val queue: RequestQueue) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(JudgeViewModel::class.java)) {
                return JudgeViewModel(
                    repository = ProjectRepository.createInstance(
                        apiService = ApiService(queue),
                        spService = SharedPreferencesService()
                    )
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}