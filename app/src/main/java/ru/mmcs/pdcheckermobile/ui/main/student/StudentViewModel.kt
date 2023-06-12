package ru.mmcs.pdcheckermobile.ui.main.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import ru.mmcs.pdcheckermobile.data.ProjectRepository
import ru.mmcs.pdcheckermobile.data.services.ApiService
import ru.mmcs.pdcheckermobile.data.services.SharedPreferencesService

class StudentViewModel(val repository: ProjectRepository) : ViewModel() {



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