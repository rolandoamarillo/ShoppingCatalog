package com.rolandoamarillo.shoppingcatalog.model

import com.rolandoamarillo.shoppingcatalog.api.response.PagingResponse

/**
 * Class representing the pagination result from a query
 */
data class Paging(
    val total: Int,
    val offset: Int,
    val limit: Int,
    val primaryResults: Int
) {

    companion object {
        /**
         * Convert method from the raw response object
         */
        fun fromPagingResponse(response: PagingResponse): Paging {
            return Paging(response.total, response.offset, response.limit, response.primaryResults)
        }
    }

}