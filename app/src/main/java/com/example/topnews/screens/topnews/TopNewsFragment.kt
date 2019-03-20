package com.example.topnews.screens.topnews

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.*
import base.BaseAdapter
import base.BaseFragment
import com.example.topnews.screens.Constants.PARCEL_FOR_ARTICLE_DETAILS
import kotlinx.android.synthetic.main.fragment_top_news.*
import sqlite.ArticlesDBHelper


class TopNewsFragment : BaseFragment<ArticleViewModel>(), BaseAdapter.OnItemClickListener<Article>,
    TopNewsAdapter.onFavouriteClickListener {

    private val articlesDBHelper by lazy {
        ArticlesDBHelper(activity!!)
    }

    private val adapterTopNews: TopNewsAdapter by lazy {
        TopNewsAdapter().apply {
            oneClickListener = this@TopNewsFragment::onItemClick
            onStarClickListener = this@TopNewsFragment::onStarClick
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_top_news
    override fun getClassTypeVM(): Class<ArticleViewModel> = ArticleViewModel::class.java

    override fun initView() {
        setupRecyclerView()
        observeForData()
    }

    private fun observeForData() {
        viewModel.getNetworkResults().observe(this, Observer {
            adapterTopNews.setData(it)
        })

        viewModel.getArticles()
    }

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

    override fun onStarClick(article: Article) {
        if (articlesDBHelper.insertArticle(article))
            Toast.makeText(activity,"Article successfully added to favourites!", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(activity,"Article successfully removed from favourites!", Toast.LENGTH_LONG).show()

        Log.d("BAZA", articlesDBHelper.readAllArticles().toString())
    }
}