package ru.mmcs.pdcheckermobile.data

import ru.mmcs.pdcheckermobile.data.services.ApiService
import ru.mmcs.pdcheckermobile.data.services.AuthenticationService
import ru.mmcs.pdcheckermobile.data.services.SharedPreferencesService

class ProjectRepository(val spService: SharedPreferencesService, val apiService: ApiService) {



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