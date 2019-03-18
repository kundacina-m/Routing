package com.example.topnews.screens.topnews

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.*
import base.BaseAdapter
import base.BaseFragment
import com.example.topnews.screens.Constants.PARCEL_FOR_ARTICLE_DETAILS
import kotlinx.android.synthetic.main.fragment_top_news.*


class TopNewsFragment : BaseFragment<ArticleViewModel>(), BaseAdapter.OnItemClickListener<Article> {

    private val adapterTopNews: TopNewsAdapter by lazy {
        TopNewsAdapter().apply {
            oneClickListener = this@TopNewsFragment::onItemClick
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_top_news
    override fun getClassTypeVM(): Class<ArticleViewModel> = ArticleViewModel::class.java

    override fun initView() {
        setupRecyclerView()
        observeForData()
    }

    private fun observeForData() =
        viewModel.getArticles().observe(this, Observer { listArticles ->
            listArticles?.let { adapterTopNews.setData(it) }
        })

    private fun setupRecyclerView() =
        rwTopNews.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = adapterTopNews
        }

    override fun onItemClick(dataItem: Article) =
        navigateToArticleDetails(Bundle().apply { putParcelable(PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

    private fun navigateToArticleDetails(bundle: Bundle) =
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.action_topNewsFragment_to_articleDetailsFragment, bundle)
}