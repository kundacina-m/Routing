package com.example.topnews.screens.categories

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseFragment
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.screens.categories.itemsincategory.ItemsAdapter
import com.example.topnews.screens.categories.itemsincategory.ItemsInCategoryViewModel
import com.example.topnews.screens.categories.seemore.CategoryModule
import com.example.topnews.utils.Constants
import com.example.topnews.utils.Constants.ARG_CATEGORY
import kotlinx.android.synthetic.main.fragment_categories.rvCategories
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top
import javax.inject.Inject

val categories = listOf("Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")

class CategoriesFragment : BaseFragment<ItemsInCategoryViewModel>() {

	@Inject lateinit var adapterCategories: CategoriesAdapter

	override fun getLayoutId(): Int = R.layout.fragment_categories
	override fun getClassTypeVM(): Class<ItemsInCategoryViewModel> = ItemsInCategoryViewModel::class.java

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		CategoryModule.registerLifecycle(lifecycle)
	}

	override fun initView() {

		actionBarSetup()
		setupRecyclerView()
		subscribeClickListeners()

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

	fun onCategoryClick(dataItem: String) =
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.articlesCategoryFragment, Bundle().apply { putString(ARG_CATEGORY, dataItem) })

	fun bindAdapterToLiveData(category: String, adapter: ItemsAdapter) {
		viewModel.initLiveData(category).observe(this@CategoriesFragment, Observer {
			if (it is OnSuccess) {
				adapter.submitData(it.item, category)
				adapterCategories.notifyDataArrived(category)
			} else adapterCategories.notifyDataArrived(category, it as OnError<Nothing>)
		})

		viewModel.fetchArticles(category)

	}

	private fun subscribeClickListeners() =
		CategoryModule.apply {
			articleClick.subscribe { articleClicked(it as Article) }
			categoryClick.subscribe { onCategoryClick(it as String) }
		}

	private fun articleClicked(dataItem: Article) =
		navigateToArticleDetails(Bundle().apply { putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

	private fun navigateToArticleDetails(bundle: Bundle) =
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.action_categoriesFragment_to_articleDetailsFragment, bundle)

}
