package com.example.topnews.screens.readlater


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topnews.R
import com.example.topnews.screens.*
import base.BaseAdapter
import base.BaseFragment
import com.example.topnews.screens.frame.FrameActivity
import com.example.topnews.utils.Constants.PARCEL_FOR_ARTICLE_DETAILS

import kotlinx.android.synthetic.main.fragment_read_later.*


class ReadLaterFragment : BaseFragment<ReadLaterViewModel>(), BaseAdapter.OnItemClickListener<Article>,
    ReadLaterAdapter.PopUpMenu {

    private var loading = false
    private lateinit var menu: Menu

    private val adapterReadLater by lazy {
        ReadLaterAdapter().apply {
            oneClickListener = this@ReadLaterFragment::onItemClick
            handleMenu = this@ReadLaterFragment::showMenu
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_read_later
    override fun getClassTypeVM(): Class<ReadLaterViewModel> = ReadLaterViewModel::class.java


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        setHasOptionsMenu(true)

        (activity!! as FrameActivity).voidSelection = { this@ReadLaterFragment.inSelectionMode() }
    }

    override fun initView() {
        setupRecyclerView()
        fetchData()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        setMenuClickListeners()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setObservers() {
        viewModel.getFavouritesFromDB().observe(this@ReadLaterFragment, Observer {
            adapterReadLater.setData(it)
            loading = false
        })
    }

    private fun setMenuClickListeners() {
        menu.findItem(R.id.selectAll).setOnMenuItemClickListener {
            adapterReadLater.checkAll = true
            true
        }

        menu.findItem(R.id.removeSelected).setOnMenuItemClickListener {
            viewModel.removeItems(adapterReadLater.checkedArticles)
            adapterReadLater.checkedArticles = arrayListOf()
            true
        }
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

    override fun showMenu(visibility: Boolean) {
        menu.findItem(R.id.selectAll).isVisible = visibility
        menu.findItem(R.id.removeSelected).isVisible = visibility
        menu.findItem(R.id.search).isVisible = visibility.not()

    }

    private fun inSelectionMode(): Boolean {
        return if (!adapterReadLater.selectionInProgress) { false } else {
            adapterReadLater.apply {
                checkAll = false
                selectionInProgress = false
            } ; true
        }
    }

    override fun onItemClick(dataItem: Article) =
        navigateToArticleDetails(Bundle().apply { putParcelable(PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

    private fun navigateToArticleDetails(bundle: Bundle) =
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.action_readLaterFragment_to_articleDetailsFragment, bundle)

}