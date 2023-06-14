package ru.mmcs.pdcheckermobile.ui.main.student

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.volley.RequestQueue
import kotlinx.coroutines.launch
import ru.mmcs.pdcheckermobile.data.ProjectRepository
import ru.mmcs.pdcheckermobile.data.models.Grade
import ru.mmcs.pdcheckermobile.utils.Result
import ru.mmcs.pdcheckermobile.data.models.Project
import ru.mmcs.pdcheckermobile.data.services.ApiService
import ru.mmcs.pdcheckermobile.data.services.SharedPreferencesService

class StudentViewModel(val repository: ProjectRepository) : ViewModel() {
    val project = MutableLiveData<Project?>()
    val isLoadingInProgress = MutableLiveData(false)
    val gradeRvAdapter = GradeRvAdapter(listOf())

    init{
        isLoadingInProgress.value = true
        viewModelScope.launch {
            val result = repository.getProjectInfo()
            if(result is Result.Success){
                project.value = result.data
                gradeRvAdapter.updateItems(result.data.grades)
            }
            isLoadingInProgress.value = false
        }

    }

    class StudentViewModelFactory(val queue: RequestQueue) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
                return StudentViewModel(
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