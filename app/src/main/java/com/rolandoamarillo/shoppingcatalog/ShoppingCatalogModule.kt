package com.rolandoamarillo.shoppingcatalog

import com.rolandoamarillo.shoppingcatalog.api.SearchApi
import com.rolandoamarillo.shoppingcatalog.repositories.SearchRepository
import com.rolandoamarillo.shoppingcatalog.repositories.SearchRepositoryImpl
import com.rolandoamarillo.shoppingcatalog.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Module to define instances for injection
 */
val shoppingCatalogModule = module {

    //Retrofit singleton instance
    single {
        Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/sites/MCO/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //SearchApi singleton instance
    single {
        get<Retrofit>().create(SearchApi::class.java)
    }

    //SearchRepository singleton instance
    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }

    //SearchViewModel to bind on SearchFragment
    viewModel { SearchViewModel(get()) }

}