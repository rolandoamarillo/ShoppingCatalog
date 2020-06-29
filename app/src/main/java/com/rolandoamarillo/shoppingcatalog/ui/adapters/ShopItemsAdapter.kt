package com.rolandoamarillo.shoppingcatalog.ui.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rolandoamarillo.shoppingcatalog.R
import com.rolandoamarillo.shoppingcatalog.model.QuerySearch
import com.rolandoamarillo.shoppingcatalog.model.ShopItem
import com.rolandoamarillo.shoppingcatalog.model.ViewType
import com.rolandoamarillo.shoppingcatalog.ui.adapters.base.BasePagingAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_header_row.view.*
import kotlinx.android.synthetic.main.search_item_row.view.*
import java.text.NumberFormat

/**
 * Adapter to the shop items
 */
class ShopItemsAdapter : BasePagingAdapter<ViewType>() {

    /**
     * Reference to the last query search
     */
    private var querySearch: QuerySearch? = null

    /**
     * Instance for header view
     */
    private val headerRow = object : ViewType() {
        override fun getViewType(): Int = HEADER
    }

    /**
     * Instance for footer view
     */
    private val footerRow = object : ViewType() {
        override fun getViewType(): Int = FOOTER
    }

    /**
     * Listener for the items click
     */
    var listener: OnShopItemClickListener? = null

    override fun createHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_header_row, parent, false)
        return ShopItemHeaderViewHolder(view)
    }

    override fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_item_row, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun createFooterViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_footer_row, parent, false)
        return ShopItemFooterViewHolder(view)
    }

    override fun bindHeaderViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val vh = viewHolder as ShopItemHeaderViewHolder
        vh.bind(querySearch)
    }

    override fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder?, position: Int) {
        val vh = viewHolder as ShopItemViewHolder
        vh.bind(items[position] as ShopItem)
    }

    override fun bindFooterViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val vh = viewHolder as ShopItemFooterViewHolder
        vh.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    /**
     * Sets the QuerySearch object and sets the items needed
     */
    fun setQuerySearch(querySearch: QuerySearch) {
        this.querySearch = querySearch
        if (items.isEmpty()) {
            add(headerRow)
        } else {
            remove(footerRow)
        }
        addAll(querySearch.items)
        if (!isLastPage()) {
            add(footerRow)
        }
    }

    /**
     * Clears the QuerySearch and the adapter
     */
    fun clearQuerySearch() {
        this.querySearch = null
        items.clear()
        notifyDataSetChanged()
    }

    /**
     * Returns true if QuerySearch response is last page
     */
    fun isLastPage(): Boolean {
        return querySearch?.isLastPage() ?: true
    }

    /**
     * ViewHolder for the header
     */
    inner class ShopItemHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Binds the header
         */
        fun bind(item: QuerySearch?) = with(itemView) {
            item?.let {
                headerTextView.text =
                    context.getString(R.string.found_items, item.paging.total)
            }
        }
    }

    /**
     * ViewHolder for the items
     */
    inner class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Binds the item
         */
        fun bind(item: ShopItem) = with(itemView) {
            titleTextView.text = item.title

            val format = NumberFormat.getCurrencyInstance()

            priceTextView.text =
                context.getString(R.string.price_format, format.format(item.price), item.currencyId)

            if (!TextUtils.isEmpty(item.thumbnail)) {
                Picasso.get().load(item.thumbnail).into(thumbnailImageView)
            }

            setOnClickListener {
                listener?.onShopItemClick(item)
            }
        }
    }

    /**
     * ViewHolder for the footer
     */
    inner class ShopItemFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Binds the footer
         */
        fun bind() = with(itemView) {
            // Not necessary for current development
        }
    }

    /**
     * Interface to send clicks from ShopItem
     */
    interface OnShopItemClickListener {

        /**
         * Called when a ShopItem is clicked
         */
        fun onShopItemClick(shopItem: ShopItem)
    }

}