package com.example.topnews.screens.categories

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseAdapter
import base.BaseFragment
import com.example.topnews.R
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants.ARG_CATEGORY
import kotlinx.android.synthetic.main.fragment_categories.rvCategories
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top

private val categories = listOf("Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")

class CategoriesFragment : BaseFragment<ItemsInCategoryViewModel>(), BaseAdapter.OnItemClickListener<String>,
						   CategoriesAdapter.BindAdapterToLiveData {

	override fun getLayoutId(): Int = R.layout.fragment_categories
	override fun getClassTypeVM(): Class<ItemsInCategoryViewModel> = ItemsInCategoryViewModel::class.java

	private val adapterCategories: CategoriesAdapter by lazy {
		CategoriesAdapter().apply {
			setData(categories)
			oneClickListener = this@CategoriesFragment::onItemClick
			handleLiveData = this@CategoriesFragment::bindAdapterToLiveData
		}
	}

	override fun initView() {
		actionBarSetup()
		setupRecyclerView()
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		menu.clear()
		inflater.inflate(R.menu.default_menu, menu)
		handleSearchMenu(menu.findItem(R.id.search))
		super.onCreateOptionsMenu(menu, inflater)
	}

	private fun actionBarSetup() {
		setActionBar(toolbar_top)
		actionBar?.title = getString(R.string.categories)
	}

	private fun setupRecyclerView() =
		rvCategories.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = this@CategoriesFragment.adapterCategories
		}

	override fun onItemClick(dataItem: String) {
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.articlesCategoryFragment, Bundle().apply { putString(ARG_CATEGORY, dataItem) })
	}

	override fun bindAdapterToLiveData(category: String, adapter: ItemsAdapter) {
		viewModel.initLiveData(category).observe(this@CategoriesFragment, Observer {
			if (it is OnSuccess) {
				adapter.setData(it.item)
				adapterCategories.notifyDataArrived(category)
			} else adapterCategories.notifyDataArrived(category, it as OnError<Nothing>)
		})

		viewModel.fetchArticles(category)

	}

}
