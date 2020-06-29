package com.rolandoamarillo.shoppingcatalog.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rolandoamarillo.shoppingcatalog.api.ApiResult
import com.rolandoamarillo.shoppingcatalog.model.QuerySearch
import com.rolandoamarillo.shoppingcatalog.repositories.SearchRepository
import kotlinx.coroutines.launch

/**
 * MainViewModel designed to retrieve the results from search query
 */
class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _search: MutableLiveData<QuerySearch> by lazy { MutableLiveData<QuerySearch>() }

    /**
     * LiveData that emits QuerySearch responses from query
     */
    val search: LiveData<QuerySearch> get() = _search


    private val _searchError: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    /**
     * LiveData that emits errors from search functionality
     */
    val searchError: LiveData<String> get() = _searchError

    private val _searchException: MutableLiveData<Throwable> by lazy { MutableLiveData<Throwable>() }

    /**
     * LiveData that emits exceptions from search functionality
     */
    val searchException: LiveData<Throwable> get() = _searchException


    private var _searchLoading = false

    /**
     * True while search is processing, false otherwise
     */
    val searchLoading: Boolean get() = _searchLoading


    /**
     * Search items from the API based on a query and an offset
     */
    fun search(query: String?, offset: Int) {
        query?.let {
            if (!_searchLoading) {
                _searchLoading = true
                viewModelScope.launch {
                    when (val searchResult = searchRepository.search(it, offset)) {
                        is ApiResult.Success -> {
                            _search.postValue(searchResult.data)
                        }
                        is ApiResult.Error -> {
                            _searchError.postValue(searchResult.error)
                        }
                        is ApiResult.Exception -> {
                            _searchException.postValue(searchResult.exception)
                        }
                    }
                    _searchLoading = false
                }
            }
        }
    }
}