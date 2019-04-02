package com.example.topnews.screens.categories

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseAdapter
import base.BaseFragment
import com.example.topnews.R
import com.example.topnews.screens.readlater.ReadLaterViewModel
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.toolbar_default.*

private val categories = listOf("Business","Entertainment","General","Health","Science","Sports","Technology")

class CategoriesFragment : BaseFragment<ReadLaterViewModel>(), BaseAdapter.OnItemClickListener<String> {

    override fun getLayoutId(): Int = R.layout.fragment_categories
    override fun getClassTypeVM(): Class<ReadLaterViewModel> = ReadLaterViewModel::class.java

    private val adapterCategories: CategoriesAdapter by lazy {
        CategoriesAdapter().apply {
            setData(categories)
            oneClickListener = this@CategoriesFragment::onItemClick
        }
    }

    override fun initView() {
        toolbarSetup()
        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.default_menu, menu)
        handleSearchMenu(menu.findItem(R.id.search))
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun toolbarSetup(){
        setActionBar(toolbar_top)
        actionBar?.title = getString(R.string.categories)
    }

    private fun setupRecyclerView() =
        rvCategories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterCategories
        }

    override fun onItemClick(dataItem: String) {
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.articlesCategoryFragment, Bundle().apply { putString("Category", dataItem) })
    }

}
