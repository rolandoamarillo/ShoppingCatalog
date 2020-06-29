package com.rolandoamarillo.shoppingcatalog.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rolandoamarillo.shoppingcatalog.R
import com.rolandoamarillo.shoppingcatalog.model.ShopItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.*
import java.text.NumberFormat

/**
 * Fragment that shows a detail from a shop item
 */
class DetailFragment : Fragment() {

    companion object {

        /**
         * Constant to define the ShopItem param
         */
        private const val SHOP_ITEM_PARAM = "shopItemParam"

        /**
         * Creates a new fragment instance with the information required
         */
        fun newInstance(shopItem: ShopItem): DetailFragment {
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(SHOP_ITEM_PARAM, shopItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments!!.getParcelable<ShopItem>(SHOP_ITEM_PARAM)!!

        titleTextView.text = item.title

        val format = NumberFormat.getCurrencyInstance()

        priceTextView.text =
            getString(R.string.price_format, format.format(item.price), item.currencyId)

        if (!TextUtils.isEmpty(item.thumbnail)) {
            Picasso.get().load(item.thumbnail).into(thumbnailImageView)
        }
    }

}