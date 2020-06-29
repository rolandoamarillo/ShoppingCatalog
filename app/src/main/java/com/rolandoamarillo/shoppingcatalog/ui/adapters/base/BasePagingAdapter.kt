package com.rolandoamarillo.shoppingcatalog.ui.adapters.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Adapter that allows paging, header and footer items
 */
abstract class BasePagingAdapter<T> : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        /**
         * Constant for identification of headers
         */
        const val HEADER = 0

        /**
         * Constant for identification of items
         */
        const val ITEM = 1

        /**
         * Constant for identification of footers
         */
        const val FOOTER = 2
    }

    /**
     * List of items
     */
    protected val items = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewHolder: ViewHolder? = when (viewType) {
            HEADER -> createHeaderViewHolder(parent)
            ITEM -> createItemViewHolder(parent)
            FOOTER -> createFooterViewHolder(parent)
            else -> null
        }

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEADER -> bindHeaderViewHolder(holder)
            ITEM -> bindItemViewHolder(holder, position)
            FOOTER -> bindFooterViewHolder(holder)
        }
    }

    override fun getItemCount(): Int = items.size

    /**
     * Creates a ViewHolder for the Header type
     */
    protected abstract fun createHeaderViewHolder(parent: ViewGroup): ViewHolder

    /**
     * Creates a ViewHolder for the Item type
     */
    protected abstract fun createItemViewHolder(parent: ViewGroup): ViewHolder

    /**
     * Creates a ViewHolder for the Footer type
     */
    protected abstract fun createFooterViewHolder(parent: ViewGroup): ViewHolder

    /**
     * Binds a ViewHolder for the Header type
     */
    protected abstract fun bindHeaderViewHolder(viewHolder: ViewHolder?)

    /**
     * Creates a ViewHolder for the Item type
     */
    protected abstract fun bindItemViewHolder(viewHolder: ViewHolder?, position: Int)

    /**
     * Creates a ViewHolder for the Footer type
     */
    protected abstract fun bindFooterViewHolder(viewHolder: ViewHolder?)

    /**
     * Returns the item on the position provided
     */
    fun getItem(position: Int): T {
        return items[position]
    }

    /**
     * Adds an item and refresh list
     */
    fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    /**
     * Adds all the items on the list provided refreshing the list
     */
    open fun addAll(items: List<T>) {
        for (item in items) {
            add(item)
        }
    }

    /**
     * Adds all the items on the list provided refreshing the adapter
     */
    fun remove(item: T) {
        val position = items.indexOf(item)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /**
     * Removes all items from the adapter
     */
    open fun clear() {
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    /**
     * Returns true if the list of items is empty
     */
    open fun isEmpty(): Boolean {
        return itemCount == 0
    }

    /**
     * Returns true if the position provided is the last position on the adapter
     */
    open fun isLastPosition(position: Int): Boolean {
        return position == items.size - 1
    }
}