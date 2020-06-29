package com.rolandoamarillo.shoppingcatalog.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rolandoamarillo.shoppingcatalog.R
import com.rolandoamarillo.shoppingcatalog.model.ShopItem
import com.rolandoamarillo.shoppingcatalog.ui.adapters.ShopItemsAdapter
import com.rolandoamarillo.shoppingcatalog.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Fragment that shows a search bar and the results on a list
 */
class SearchFragment : Fragment(), ShopItemsAdapter.OnShopItemClickListener {

    companion object {
        /**
         * Method to create instance of the fragment
         */
        fun newInstance() = SearchFragment()
    }

    /**
     * MainViewModel to listen to search events
     */
    private val searchViewModel: SearchViewModel by viewModel()

    /**
     * Listener to send information to parent
     */
    private lateinit var listener: SearchFragmentListener

    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var adapter: ShopItemsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as SearchFragmentListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SearchView init to open up
        searchView.setIconifiedByDefault(false)
        searchView.isIconified = false

        // Adding listener to receive text submit
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                progressView.visibility = View.VISIBLE
                searchViewModel.search(query, 0)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.clearQuerySearch()
                return true
            }
        })

        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        adapter = ShopItemsAdapter()
        adapter.listener = this
        recyclerView.adapter = adapter

        // Pagination
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)

        searchViewModel.search.observe(viewLifecycleOwner, Observer {
            progressView.visibility = View.GONE
            adapter.setQuerySearch(it)
        })

        searchViewModel.searchError.observe(viewLifecycleOwner, Observer {
            progressView.visibility = View.GONE
            Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
        })

        searchViewModel.searchException.observe(viewLifecycleOwner, Observer {
            progressView.visibility = View.GONE
            Toast.makeText(requireContext(), getString(R.string.exception), Toast.LENGTH_SHORT)
                .show()
        })
    }

    /**
     * Scroll listener for pagination.
     */
    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (!searchViewModel.searchLoading && !adapter.isLastPage() &&
                    (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= 50)
                ) {
                    val query = searchView.query.toString()
                    searchViewModel.search(query, adapter.itemCount)
                }
            }
        }

    /**
     * Listener to send information to the activity attached
     */
    interface SearchFragmentListener {
        fun onItemSelected(shopItem: ShopItem)
    }

    override fun onShopItemClick(shopItem: ShopItem) {
        listener.onItemSelected(shopItem)
    }
}