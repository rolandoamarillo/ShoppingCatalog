package com.rolandoamarillo.shoppingcatalog.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rolandoamarillo.shoppingcatalog.CoroutineTestRule
import com.rolandoamarillo.shoppingcatalog.api.ApiResult
import com.rolandoamarillo.shoppingcatalog.model.Paging
import com.rolandoamarillo.shoppingcatalog.model.QuerySearch
import com.rolandoamarillo.shoppingcatalog.model.ShopItem
import com.rolandoamarillo.shoppingcatalog.repositories.SearchRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Test for SearchViewModel
 */
class SearchViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var searchRepository: SearchRepository

    @InjectMocks
    private lateinit var searchViewModel: SearchViewModel

    @Mock
    private lateinit var searchObserver: Observer<QuerySearch>

    @Mock
    private lateinit var searchErrorObserver: Observer<String>

    @Mock
    private lateinit var searchExceptionObserver: Observer<Throwable>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchViewModel.search.observeForever(searchObserver)
        searchViewModel.searchError.observeForever(searchErrorObserver)
        searchViewModel.searchException.observeForever(searchExceptionObserver)
    }

    @After
    fun clean() {
        searchViewModel.search.removeObserver(searchObserver)
        searchViewModel.searchError.removeObserver(searchErrorObserver)
        searchViewModel.searchException.removeObserver(searchExceptionObserver)
    }

    @Test
    fun givenASuccessQueryWhenSearchThenObserveSearch() = runBlocking {
        val query = UUID.randomUUID().toString()
        val items = emptyList<ShopItem>()
        val paging = Paging(0, 0, 0, 0)
        val querySearch = QuerySearch(query, paging, items)
        `when`(
            searchRepository.search(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(ApiResult.Success(querySearch))
        searchViewModel.search(query, 0)
        verify(searchObserver).onChanged(querySearch)
        verify(searchErrorObserver, never()).onChanged(anyString())
        verify(searchExceptionObserver, never()).onChanged(any())
    }

    @Test
    fun givenAnErrorWhenSearchThenObserveSearchError() = runBlocking {
        val query = UUID.randomUUID().toString()
        val items = emptyList<ShopItem>()
        val paging = Paging(0, 0, 0, 0)
        `when`(
            searchRepository.search(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(ApiResult.Error(query))
        searchViewModel.search(query, 0)
        verify(searchObserver, never()).onChanged(any())
        verify(searchErrorObserver).onChanged(anyString())
        verify(searchExceptionObserver, never()).onChanged(any())
    }

    @Test
    fun givenAnExceptionWhenSearchThenObserveSearchException() = runBlocking {
        val query = UUID.randomUUID().toString()
        val throwable = Throwable()
        `when`(
            searchRepository.search(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(ApiResult.Exception(throwable))
        searchViewModel.search(query, 0)
        verify(searchObserver, never()).onChanged(any())
        verify(searchErrorObserver, never()).onChanged(anyString())
        verify(searchExceptionObserver).onChanged(throwable)
    }

}