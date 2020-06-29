package com.rolandoamarillo.shoppingcatalog.repositories

import com.rolandoamarillo.shoppingcatalog.CoroutineTestRule
import com.rolandoamarillo.shoppingcatalog.api.ApiResult
import com.rolandoamarillo.shoppingcatalog.api.SearchApi
import com.rolandoamarillo.shoppingcatalog.api.response.ItemResponse
import com.rolandoamarillo.shoppingcatalog.api.response.PagingResponse
import com.rolandoamarillo.shoppingcatalog.api.response.SearchResponse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.*

/**
 * Test for SearchRepository class
 */
class SearchRepositoryImplTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var searchApi: SearchApi

    @InjectMocks
    private lateinit var searchRepository: SearchRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    fun givenAResponseWhenSearchThenReturnApiResultSuccess() = runBlocking {
        val query = UUID.randomUUID().toString()
        val items = emptyList<ItemResponse>()
        val paging = PagingResponse(0, 0, 0, 0)
        val querySearch = SearchResponse(query, paging, items)
        val response: Response<SearchResponse> = Response.success(querySearch)

        `when`(
            searchApi.search(
                anyString(),
                anyInt()
            )
        ).thenReturn(response as Response<SearchResponse>)
        val searchResponse = searchRepository.search(query, 50)
        assertTrue(searchResponse is ApiResult.Success)
    }

    @Test
    fun givenAnErrorWhenSearchThenReturnApiResultError() = runBlocking<Unit> {
        val query = UUID.randomUUID().toString()
        val response: Response<SearchResponse> = Response.error(404, ResponseBody.create(null, ""))

        `when`(
            searchApi.search(
                anyString(),
                anyInt()
            )
        ).thenReturn(response as Response<SearchResponse>)
        val searchResponse = searchRepository.search(query, 50)
        assertTrue(searchResponse is ApiResult.Error)
    }

    @Test
    fun givenAnExceptionWhenSearchThenReturnApiResultError() = runBlocking<Unit> {
        val query = UUID.randomUUID().toString()
        `when`(
            searchApi.search(
                anyString(),
                anyInt()
            )
        ).thenThrow(RuntimeException())
        val searchResponse = searchRepository.search(query, 50)
        assertTrue(searchResponse is ApiResult.Exception)
    }

}