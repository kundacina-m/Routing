package com.example.topnews.screens.articlescategory

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseAdapter
import base.BaseFragment
import base.BasePagedListAdapter
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.domain.RequestError
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants
import com.example.topnews.utils.Constants.ARG_CATEGORY
import com.example.topnews.utils.Constants.ERROR_DATABASE
import kotlinx.android.synthetic.main.fragment_articles_category.rvArticlesFromCategory
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top

class ArticlesCategoryFragment : BaseFragment<ArticlesCategoryViewModel>(), BasePagedListAdapter.OnItemClickListener<Article> {

	private val adapter by lazy {
		ArticlesCategoryAdapter().apply {
			oneClickListener = this@ArticlesCategoryFragment::onItemClick
		}
	}

	private val category by lazy {
		arguments?.get(ARG_CATEGORY) as String
	}

	override fun getLayoutId(): Int = R.layout.fragment_articles_category
	override fun getClassTypeVM(): Class<ArticlesCategoryViewModel> = ArticlesCategoryViewModel::class.java

	override fun initView() {
		actionBarSetup()
		setupRecyclerView()
		observeForData()
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		menu.clear()
		inflater.inflate(R.menu.default_menu, menu)
		super.onCreateOptionsMenu(menu, inflater)
	}

	private fun actionBarSetup() {
		setActionBar(toolbar_top)
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

	override fun onItemClick(dataItem: Article) {}

	private fun handleError(onError: OnError<List<Article>>) =
		when (onError.error) {
			is RequestError.UnknownError -> Log.d(TAG, Constants.ERROR_UNKNOWN)
			is RequestError.HttpError -> Log.d(TAG, Constants.ERROR_HTTP)
			is RequestError.NoInternetError -> Log.d(TAG, Constants.ERROR_INTERNET)
			is RequestError.ServerError -> Log.d(TAG, Constants.ERROR_SERVER)
			is RequestError.DatabaseError -> Log.d(TAG, ERROR_DATABASE)

		}

}
