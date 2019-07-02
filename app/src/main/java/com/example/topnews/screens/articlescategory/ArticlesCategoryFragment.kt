package com.example.topnews.screens.articlescategory

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseFragment
import base.BasePagedListAdapter
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.domain.RequestError
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.utils.Constants
import com.example.topnews.utils.Constants.ARG_CATEGORY
import com.example.topnews.utils.Constants.ERROR_DATABASE
import com.example.topnews.utils.Constants.SHOW_NAV_BAR
import kotlinx.android.synthetic.main.activity_frame.bottom_navigation
import kotlinx.android.synthetic.main.fragment_articles_category.rvArticlesFromCategory
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top
import javax.inject.Inject

class ArticlesCategoryFragment : BaseFragment<ArticlesCategoryViewModel>() {

	@Inject lateinit var adapter: ArticlesCategoryAdapter

	private val category by lazy {
		arguments?.get(ARG_CATEGORY) as String
	}

	override fun getLayoutId(): Int = R.layout.fragment_articles_category
	override fun getClassTypeVM(): Class<ArticlesCategoryViewModel> = ArticlesCategoryViewModel::class.java

	override fun initView() {

		activity?.bottom_navigation?.visibility = View.GONE

		actionBarSetup()
		setupRecyclerView()
		observeForData()
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		menu.clear()
		inflater.inflate(R.menu.default_menu, menu)
		handleSearchMenu(menu.findItem(R.id.search))
		super.onCreateOptionsMenu(menu, inflater)
	}

	override fun onDestroy() {
		super.onDestroy()
		activity?.bottom_navigation?.visibility = View.VISIBLE
	}

	private fun actionBarSetup() {
		setActionBar(toolbar_top, true)
		actionBar?.title = category
	}

	private fun observeForData() {
		viewModel.apply {
			this.category = this@ArticlesCategoryFragment.category
			articles.observe(this@ArticlesCategoryFragment, Observer {
				adapter.submitList(it)
			})
			onError.observe(this@ArticlesCategoryFragment, Observer {
				handleError(it)
			})
		}
	}

	private fun setupRecyclerView() =
		rvArticlesFromCategory.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = this@ArticlesCategoryFragment.adapter
		}

	fun onItemClick(dataItem: Article) =
		navigateToArticleDetails(Bundle().apply {
			putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS, dataItem);
			putString(SHOW_NAV_BAR, SHOW_NAV_BAR)
		})

	private fun navigateToArticleDetails(bundle: Bundle) =
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.action_articlesCategoryFragment_to_articleDetailsFragment, bundle)

	private fun handleError(onError: OnError<List<Article>>) =
		when (onError.error) {
			is RequestError.UnknownError -> Log.d(TAG, Constants.ERROR_UNKNOWN)
			is RequestError.HttpError -> Log.d(TAG, Constants.ERROR_HTTP)
			is RequestError.NoInternetError -> Log.d(TAG, Constants.ERROR_INTERNET)
			is RequestError.ServerError -> Log.d(TAG, Constants.ERROR_SERVER)
			is RequestError.DatabaseError -> Log.d(TAG, ERROR_DATABASE)

		}

}
