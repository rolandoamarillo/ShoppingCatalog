package com.rolandoamarillo.shoppingcatalog.model

import com.rolandoamarillo.shoppingcatalog.api.response.SearchResponse

/**
 * Class representing a query result from a search
 */
data class QuerySearch(val query: String, val paging: Paging, val items: List<ShopItem>) {

    companion object {
        /**
         * Convert method from the raw response object
         */
        fun fromSearchResponse(searchResponse: SearchResponse): QuerySearch {
            val list = mutableListOf<ShopItem>()
            for (item in searchResponse.results) {
                list.add(ShopItem.fromItemResponse(item))
            }
            return QuerySearch(
                searchResponse.query,
                Paging.fromPagingResponse(searchResponse.paging),
                list
            )
        }
    }

    /**
     * Returns true if the pagination is the last for the query
     */
    fun isLastPage(): Boolean {
        return paging.offset + paging.limit >= paging.primaryResults
    }

}