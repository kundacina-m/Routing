package com.example.topnews.screens.search


import android.os.Bundle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.*
import base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment<ArticleViewModel>(), BaseAdapter.OnItemClickListener<Article> {

    private val adapterSearch: SearchAdapter by lazy {
        SearchAdapter().apply {
            oneClickListener = this@SearchFragment::onItemClick
        }
    }

    override fun getLayoutId() = R.layout.fragment_search
    override fun getClassTypeVM(): Class<ArticleViewModel> = ArticleViewModel::class.java

    override fun initView() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() =
        rvSearchResults.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterSearch
        }

    fun updateAdapter(num: Int) = adapterSearch.setData(viewModel.getNumOfArticles(num).value!!)

    override fun onItemClick(dataItem: Article) =
        navigateToArticleDetails(Bundle().apply { putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

    private fun navigateToArticleDetails(bundle: Bundle) =
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.action_searchFragment_to_articleDetailsFragment, bundle)
}