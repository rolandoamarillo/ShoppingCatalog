package com.rolandoamarillo.shoppingcatalog.api

import com.rolandoamarillo.shoppingcatalog.api.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API interface to search for items
 */
interface SearchApi {

    /**
     * Endpoint to search based from a query and a pagination offset
     */
    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("offset") offset: Int
    ): Response<SearchResponse>

}