package com.rolandoamarillo.shoppingcatalog.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rolandoamarillo.shoppingcatalog.R
import com.rolandoamarillo.shoppingcatalog.model.ShopItem
import com.rolandoamarillo.shoppingcatalog.ui.fragments.DetailFragment

/**
 * Activity that shows the details of a ShopItem
 */
class DetailActivity : AppCompatActivity() {

    companion object {

        /**
         * Constant to define the ShopItem param
         */
        private const val SHOP_ITEM_PARAM = "shopItemParam"

        /**
         * Creates an intent with the information required for this activity
         */
        fun newIntent(context: Context, shopItem: ShopItem): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(SHOP_ITEM_PARAM, shopItem)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        if (savedInstanceState == null) {
            val shopItem = intent.getParcelableExtra<ShopItem>(SHOP_ITEM_PARAM)!!
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment.newInstance(shopItem))
                .commitNow()
        }
    }
}