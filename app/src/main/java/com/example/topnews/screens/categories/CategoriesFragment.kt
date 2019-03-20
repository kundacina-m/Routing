package com.example.topnews.screens.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.FakeData
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment(), BaseAdapter.OnItemClickListener<String> {

    private val adapterCategories: CategoriesAdapter by lazy {
        CategoriesAdapter().apply {
            setData(FakeData.fetchCategories())
            oneClickListener = this@CategoriesFragment::onItemClick
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() =
        rvCategories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterCategories
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                setDrawable(AppCompatResources.getDrawable(context!!, R.drawable.divider)!!)
            })
        }

    override fun onItemClick(dataItem: String) {
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.articlesCategoryFragment, Bundle().apply { putString("Category", dataItem) })
    }
}
