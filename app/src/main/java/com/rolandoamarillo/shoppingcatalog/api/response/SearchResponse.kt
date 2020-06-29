package com.rolandoamarillo.shoppingcatalog.api.response

import com.google.gson.annotations.SerializedName

/**
 * Search Endpoint response
 */
data class SearchResponse(
    @SerializedName("query") val query: String,
    @SerializedName("paging") val paging: PagingResponse,
    @SerializedName("results") val results: List<ItemResponse>
)

/**
 * Paging response from the search endpoint
 */
data class PagingResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("primary_results") val primaryResults: Int
)

/**
 * Item response from the search endpoint
 */
data class ItemResponse(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Long,
    @SerializedName("currency_id") val currencyId: String,
    @SerializedName("thumbnail") val thumbnail: String
)
