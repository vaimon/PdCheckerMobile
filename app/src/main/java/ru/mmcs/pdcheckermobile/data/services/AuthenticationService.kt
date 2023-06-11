package ru.mmcs.pdcheckermobile.data.services

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import org.json.JSONObject
import ru.mmcs.pdcheckermobile.data.models.User
import ru.mmcs.pdcheckermobile.utils.Result
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class AuthenticationService(val requestQueue: RequestQueue) {
    private val baseUrl:String = "https://pdchecker.onrender.com/"

    suspend fun login(username: String, password: String) = suspendCoroutine<Result<JSONObject>>{ cont ->
        val payload = JSONObject()
            .put("login", username)
            .put("password", password)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, baseUrl + "login", payload,
            { response ->
                cont.resume(Result.Success(response))
            },
            { error ->
                cont.resume(Result.Error(error))
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    suspend fun getUserInfo(username: String) = suspendCoroutine<Result<User>>{ cont ->


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "${baseUrl}user/${username}", null,
            { response ->
                cont.resume(Result.Success(Gson().fromJson(response.toString(), User::class.java)))
            },
            { error ->
                cont.resume(Result.Error(error))
            }
        )
        jsonObjectRequest.setRetryPolicy(
            DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue.add(jsonObjectRequest)
    }
}