package ru.mmcs.pdcheckermobile.data.services

import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import org.json.JSONObject
import ru.mmcs.pdcheckermobile.data.models.Project
import ru.mmcs.pdcheckermobile.utils.AuthenticatedGsonRequest
import ru.mmcs.pdcheckermobile.utils.Result
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ApiService(val requestQueue: RequestQueue) {
    private val baseUrl:String = "https://pdchecker.onrender.com/"

    suspend fun getProjectInfo(jwt: String) = suspendCoroutine<Result<Project>>{ cont ->

        val jsonObjectRequest = AuthenticatedGsonRequest(
            baseUrl + "projects/get",
            Project::class.java,
            mutableMapOf("Authorization" to "Bearer $jwt"),
            { response ->
                cont.resume(Result.Success(response))
            },
            { error -> cont.resume(Result.Error(error)) }
        ).setRetryPolicy(
            DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        jsonObjectRequest.headers["Authorization"] = "Bearer $jwt"
        requestQueue.add(jsonObjectRequest)
    }
}