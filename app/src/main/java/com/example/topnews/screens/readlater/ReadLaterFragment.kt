package com.example.topnews.screens.readlater


import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topnews.R
import com.example.topnews.screens.*
import base.BaseAdapter
import base.BaseFragment
import com.example.topnews.utils.Constants.PARCEL_FOR_ARTICLE_DETAILS

import kotlinx.android.synthetic.main.fragment_read_later.*

class ReadLaterFragment : BaseFragment<ReadLaterViewModel>(), BaseAdapter.OnItemClickListener<Article> {

    private var loading = false

    private val adapterReadLater: ReadLaterAdapter by lazy {
        ReadLaterAdapter().apply {
            oneClickListener = this@ReadLaterFragment::onItemClick
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_read_later
    override fun getClassTypeVM(): Class<ReadLaterViewModel> = ReadLaterViewModel::class.java

    override fun initView() {
        setupRecyclerView()
        fetchData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        viewModel.getFavouritesFromDB().observe(this@ReadLaterFragment, Observer {
            adapterReadLater.setData(it)
            loading = false
        })
    }

    private fun fetchData() = viewModel.getArticlesFromDB()

    private fun setupRecyclerView() =
        readLaterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterReadLater
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && !viewModel.endOfDB) {
                        val totalItemCount = layoutManager?.itemCount
                        val pastVisibleItem = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                        Log.d("visibleItem", pastVisibleItem.toString())
                        Log.d("currentItems", totalItemCount.toString())
                        if (totalItemCount != null) {
                            if ((totalItemCount <= pastVisibleItem + 4) && !loading) {
                                loading = true
                                viewModel.getArticlesFromDB()
                            }
                        }
                    }
                }
            })
        }

    override fun onItemClick(dataItem: Article) =
        navigateToArticleDetails(Bundle().apply { putParcelable(PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

    private fun navigateToArticleDetails(bundle: Bundle) =
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.action_readLaterFragment_to_articleDetailsFragment, bundle)
}