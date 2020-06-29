package com.rolandoamarillo.shoppingcatalog.repositories

import com.rolandoamarillo.shoppingcatalog.api.ApiResult
import com.rolandoamarillo.shoppingcatalog.model.QuerySearch

/**
 * Repository for search functionality
 */
interface SearchRepository {

    suspend fun search(query: String, offset: Int): ApiResult<QuerySearch>

}