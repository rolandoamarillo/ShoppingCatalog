package com.rolandoamarillo.shoppingcatalog

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Application extension for initialization
 */
class ShoppingCatalogApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Starting Koin
        startKoin {
            androidLogger()
            androidContext(this@ShoppingCatalogApplication)
            modules(shoppingCatalogModule)
        }

    }

}