package com.rolandoamarillo.shoppingcatalog.repositories

import com.rolandoamarillo.shoppingcatalog.api.ApiResult
import com.rolandoamarillo.shoppingcatalog.api.SearchApi
import com.rolandoamarillo.shoppingcatalog.model.QuerySearch
import com.rolandoamarillo.shoppingcatalog.repositories.base.Repository

/**
 * SearchRepository implementation
 */
class SearchRepositoryImpl(private val searchApi: SearchApi) : Repository(), SearchRepository {

    /**
     * Search by query and offset
     */
    override suspend fun search(query: String, offset: Int): ApiResult<QuerySearch> {
        return apiCall({ searchApi.search(query, offset) }, { QuerySearch.fromSearchResponse(it) })
    }

}