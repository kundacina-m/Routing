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
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants
import com.example.topnews.utils.Constants.ARG_CATEGORY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_categories.rvCategories
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top

private val categories = listOf("Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")
lateinit var disposable: Disposable

class CategoriesFragment : BaseFragment<ItemsInCategoryViewModel>(), BaseAdapter.OnItemClickListener<String>,
						   CategoriesAdapter.BindAdapterToLiveData {

	override fun getLayoutId(): Int = R.layout.fragment_categories
	override fun getClassTypeVM(): Class<ItemsInCategoryViewModel> = ItemsInCategoryViewModel::class.java

	private val adapterCategories: CategoriesAdapter by lazy {
		CategoriesAdapter().apply {
			setData(categories)
			clickListener = this@CategoriesFragment::onItemClick
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

	override fun onResume() {
		super.onResume()
		disposable = ArticleClickedEventBus.subscribe<Article>()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribeBy {
				articleClicked(it)
			}
	}

	override fun onPause() {
		super.onPause()
		disposable.dispose()
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

	private fun articleClicked(dataItem: Article) =
		navigateToArticleDetails(Bundle().apply { putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

	private fun navigateToArticleDetails(bundle: Bundle) =
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.action_categoriesFragment_to_articleDetailsFragment, bundle)

}
