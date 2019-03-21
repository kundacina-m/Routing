package com.example.topnews.screens.categories

import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import base.BaseFragment
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : BaseFragment<RVViewModel>() {

    private val adapterCategories: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }

    override fun getLayoutId(): Int = R.layout.fragment_categories
    override fun getClassTypeVM(): Class<RVViewModel> = RVViewModel::class.java

    override fun initView() {
        setupRecyclerView()
        observeForData()
    }

    private fun observeForData() =
        viewModel.getRV().observe(this, Observer { listRV ->
            listRV?.let { adapterCategories.setData(listRV) }
        })

    private fun setupRecyclerView() =
        rvCategories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterCategories
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                setDrawable(AppCompatResources.getDrawable(context!!, R.drawable.divider)!!)
            })
        }

}
