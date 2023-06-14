package ru.mmcs.pdcheckermobile.utils

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * Make a GET request and return a parsed object from JSON.
 *
 * @param url URL of the request to make
 * @param clazz Relevant class object, for Gson's reflection
 * @param headers Map of request headers
 */
class AuthenticatedGsonRequest<T>(
    url: String,
    private val clazz: Class<T>,
    private val headers: MutableMap<String, String>?,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : Request<T>(Method.GET, url, errorListener) {
    private val gson = Gson()

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
            Response.success(
                gson.fromJson(json, clazz),
                HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }
}

class AuthenticatedGsonListRequest<T>(
    url: String,
    private val clazz: Class<T>,
    private val headers: MutableMap<String, String>?,
    private val listener: Response.Listener<List<T>>, // Update the listener type
    errorListener: Response.ErrorListener
) : Request<List<T>>(Method.GET, url, errorListener) { // Update the parent class and return type
    private val gson = Gson()

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: List<T>) = listener.onResponse(response) // Update the response type

    override fun parseNetworkResponse(response: NetworkResponse?): Response<List<T>> { // Update the response type
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            val listType = TypeToken.getParameterized(List::class.java, clazz).type // Create a list type
            val list = gson.fromJson<List<T>>(json, listType) // Deserialize the JSON into a list
            Response.success(
                list,
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }
}