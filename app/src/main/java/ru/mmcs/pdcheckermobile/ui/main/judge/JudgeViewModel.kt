package ru.mmcs.pdcheckermobile.ui.main.judge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.volley.RequestQueue
import kotlinx.coroutines.launch
import ru.mmcs.pdcheckermobile.data.LoginRepository
import ru.mmcs.pdcheckermobile.data.ProjectRepository
import ru.mmcs.pdcheckermobile.data.models.Project
import ru.mmcs.pdcheckermobile.data.services.ApiService
import ru.mmcs.pdcheckermobile.data.services.AuthenticationService
import ru.mmcs.pdcheckermobile.data.services.SharedPreferencesService
import ru.mmcs.pdcheckermobile.ui.login.viewmodels.LoginViewModel
import ru.mmcs.pdcheckermobile.utils.Result

class JudgeViewModel(val repository: ProjectRepository) : ViewModel() {

    val projectsRvAdapter = ProjectsRvAdapter(listOf(), object : ProjectsRvAdapter.OnItemInteractionListener {
        override fun onItemClicked(item: Project?) {

        }
    })
    val isLoadingInProgress = MutableLiveData(false)

    init{
        isLoadingInProgress.value = true
        viewModelScope.launch {
            val result = repository.getAllProjects()
            if(result is Result.Success){
                projectsRvAdapter.updateItems(result.data)
            }
            isLoadingInProgress.value = false
        }
    }

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