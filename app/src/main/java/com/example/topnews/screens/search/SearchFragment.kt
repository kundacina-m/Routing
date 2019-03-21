package com.example.topnews.screens.search


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.*
import base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
import com.example.topnews.utils.Constants

class SearchFragment : BaseFragment<SearchViewModel>(), BaseAdapter.OnItemClickListener<Article> {

    private var loading = false

    private val adapterSearch: SearchAdapter by lazy {
        SearchAdapter().apply {
            oneClickListener = this@SearchFragment::onItemClick
        }
    }

    override fun getLayoutId() = R.layout.fragment_search
    override fun getClassTypeVM(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView() {
        setupRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    private fun setupRecyclerView() =
        rvSearchResults.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterSearch
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val totalItemCount = layoutManager?.itemCount
                        val pastVisibleItem = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                        Log.d("visibleItem", pastVisibleItem.toString())
                        Log.d("currentItems", totalItemCount.toString())
                        Log.d("totalResults", viewModel.totalResults.toString())
                        if (totalItemCount != null && totalItemCount < viewModel.totalResults) {
                            if ((totalItemCount <= pastVisibleItem + 5) && !loading) {
                                loading = true
                                viewModel.getArticlesForQueryOnScroll()
                            }
                        }
                    }
                }
            })
        }

    private fun setObservers() = viewModel.getNetworkSearchResults().observe(this, Observer {
        adapterSearch.setData(it)
        loading = false
    })

    fun fetchData(searchKeyword: String) = viewModel.getArticlesForQuery(searchKeyword)

    override fun onItemClick(dataItem: Article) =
        navigateToArticleDetails(Bundle().apply { putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

    private fun navigateToArticleDetails(bundle: Bundle) =
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.action_searchFragment_to_articleDetailsFragment, bundle)
}