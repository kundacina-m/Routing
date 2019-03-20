package com.example.topnews.screens.readlater


import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.*
import base.BaseAdapter
import base.BaseFragment
import com.example.topnews.screens.Constants.PARCEL_FOR_ARTICLE_DETAILS

import kotlinx.android.synthetic.main.fragment_read_later.*

class ReadLaterFragment : BaseFragment<ArticleViewModel>(), BaseAdapter.OnItemClickListener<Article> {

    private val adapterReadLater: ReadLaterAdapter by lazy {
        ReadLaterAdapter().apply {
            oneClickListener = this@ReadLaterFragment::onItemClick
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_read_later
    override fun getClassTypeVM(): Class<ArticleViewModel> = ArticleViewModel::class.java

    override fun initView() {
        setupRecyclerView()
        observeForData()
    }

    private fun observeForData() {
        viewModel.apply {
            getNetworkResults().observe(this@ReadLaterFragment, Observer { adapterReadLater.setData(it) })
            getArticlesFromDB(activity!!)
        }
    }

    private fun setupRecyclerView() =
        readLaterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterReadLater
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                setDrawable(getDrawable(context!!, R.drawable.divider)!!)
            })
        }

    override fun onItemClick(dataItem: Article) =
        navigateToArticleDetails(Bundle().apply { putParcelable(PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

    private fun navigateToArticleDetails(bundle: Bundle) =
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.action_readLaterFragment_to_articleDetailsFragment, bundle)
}