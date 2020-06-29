package com.rolandoamarillo.shoppingcatalog.api

/**
 * Class that represents an api result and contains the object from the operation.
 * If operation was completed, success will contain the parsed object.
 * If there was an error with the request, it will contain the message of error.
 * If an exception was thrown, it will contain the throwable.
 */
sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val error: String) : ApiResult<Nothing>()
    data class Exception(val exception: Throwable) : ApiResult<Nothing>()
}