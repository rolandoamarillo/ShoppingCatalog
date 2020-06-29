package com.rolandoamarillo.shoppingcatalog.repositories.base

import android.util.Log
import com.rolandoamarillo.shoppingcatalog.api.ApiResult
import retrofit2.Response

/**
 * Base class for the repositories.
 */
abstract class Repository {

    /**
     * Converts a network call to ApiResult that will be emitted to a LiveData
     */
    suspend fun <R : Any, V : Any> apiCall(
        call: suspend () -> Response<R>,
        convert: (value: R) -> V
    ): ApiResult<V> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                val success = convert(body)
                ApiResult.Success(success)
            } else {
                ApiResult.Error("There was an error processing your request")
            }

        } catch (e: Exception) {
            Log.e("ApiResult", "There was an error with the api consumption", e)
            ApiResult.Exception(e)
        }
    }
}