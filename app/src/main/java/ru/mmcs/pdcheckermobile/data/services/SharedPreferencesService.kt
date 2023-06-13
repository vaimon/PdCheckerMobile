package ru.mmcs.pdcheckermobile.data.services

import android.content.Context
import android.content.SharedPreferences
import ru.mmcs.pdcheckermobile.App

class SharedPreferencesService {

    fun saveJwtToken(token: String){
        App.appContext
        ?.getSharedPreferences("ru.mmcs.pdchecker", Context.MODE_PRIVATE)
        ?.edit()
            ?.putString("jwt", token)
            ?.apply()
    }

    fun saveRole(role: String){
        App.appContext
            ?.getSharedPreferences("ru.mmcs.pdchecker", Context.MODE_PRIVATE)
            ?.edit()
            ?.putString("role", role)
            ?.apply()
    }

    fun readJwtToken(): String?{
        return App.appContext
            ?.getSharedPreferences("ru.mmcs.pdchecker", Context.MODE_PRIVATE)
            ?.getString("jwt",null)
    }

    fun readRole(): String?{
        return App.appContext
            ?.getSharedPreferences("ru.mmcs.pdchecker", Context.MODE_PRIVATE)
            ?.getString("role",null)
    }
}