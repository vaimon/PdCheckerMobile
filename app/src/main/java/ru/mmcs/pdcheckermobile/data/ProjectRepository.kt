package ru.mmcs.pdcheckermobile.data

import ru.mmcs.pdcheckermobile.data.models.Project
import ru.mmcs.pdcheckermobile.data.services.ApiService
import ru.mmcs.pdcheckermobile.data.services.AuthenticationService
import ru.mmcs.pdcheckermobile.data.services.SharedPreferencesService
import ru.mmcs.pdcheckermobile.utils.Result

class ProjectRepository(val spService: SharedPreferencesService, val apiService: ApiService) {


    suspend fun getProjectInfo(): Result<Project> {
        return apiService.getProjectInfo(spService.readJwtToken() ?: "")
    }

    // Dagger, pls come and help me
    companion object{
        private lateinit var repository: ProjectRepository

        fun createInstance(apiService: ApiService, spService: SharedPreferencesService) : ProjectRepository{
            repository = ProjectRepository(spService, apiService)
            return repository
        }

        fun getInstance(): ProjectRepository{
            return repository
        }
    }
}