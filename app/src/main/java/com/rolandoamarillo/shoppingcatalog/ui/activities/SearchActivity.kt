package com.rolandoamarillo.shoppingcatalog.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rolandoamarillo.shoppingcatalog.R
import com.rolandoamarillo.shoppingcatalog.model.ShopItem
import com.rolandoamarillo.shoppingcatalog.ui.fragments.SearchFragment

/**
 * Activity that allows search for items
 */
class SearchActivity : AppCompatActivity(), SearchFragment.SearchFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance())
                .commitNow()
        }
    }

    override fun onItemSelected(shopItem: ShopItem) {
        val intent = DetailActivity.newIntent(this, shopItem)
        startActivity(intent)
    }
}