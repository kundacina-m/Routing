package com.example.topnews.screens.articlescategory

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseFragment
import com.example.topnews.R
import com.example.topnews.data.Article
import com.example.topnews.screens.newactivity.TestObj
import kotlinx.android.synthetic.main.fragment_articles_category.rvArticlesFromCategory
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top
import javax.inject.Inject

class ArticlesCategoryFragment : BaseFragment<ArticlesCategoryViewModel, ArticlesCategoryRoute>() {

	@Inject
	lateinit var testObj: TestObj

	private val adapter by lazy {
		ArticlesCategoryAdapter().apply {
			clickListener = this@ArticlesCategoryFragment::onItemClick
		}
	}

	override fun getLayoutId(): Int = R.layout.fragment_articles_category

	override fun initView() {
		Log.d("CategoryFragment", testObj.toString())

		actionBarSetup()
		setupRecyclerView()
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		menu.clear()
		inflater.inflate(R.menu.default_menu, menu)
		super.onCreateOptionsMenu(menu, inflater)
	}

	private fun actionBarSetup() {
		setActionBar(toolbar_top)
		actionBar?.title = "Articles"
	}

	override fun setObservers() =
		viewModel.articles.observe(this@ArticlesCategoryFragment, Observer {
			adapter.setData(it)
		})

	private fun setupRecyclerView() =
		rvArticlesFromCategory.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = this@ArticlesCategoryFragment.adapter
		}

	private fun onItemClick(dataItem: Article) =
		navigation.goToDetails(dataItem,"option1","option2", arrayListOf(1,2,3))

}
