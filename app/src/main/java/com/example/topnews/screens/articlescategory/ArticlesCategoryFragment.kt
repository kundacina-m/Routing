package com.example.topnews.screens.articlescategory

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseAdapter
import base.BaseFragment
import com.example.topnews.R
import com.example.topnews.screens.Article
import kotlinx.android.synthetic.main.fragment_articles_category.rvArticlesFromCategory

class ArticlesCategoryFragment : BaseFragment<ArticlesCategoryViewModel>(), BaseAdapter.OnItemClickListener<Article> {

	private val adapterArticlesCategory by lazy {
		ArticlesCategoryAdapter().apply {
			oneClickListener = this@ArticlesCategoryFragment::onItemClick
		}
	}

	private val category by lazy {
		arguments?.get("Category") as String
	}

	override fun getLayoutId(): Int = R.layout.fragment_articles_category
	override fun getClassTypeVM(): Class<ArticlesCategoryViewModel> = ArticlesCategoryViewModel::class.java

	override fun initView() {
		setupRecyclerView()
		observeForData()
	}

	private fun observeForData() {
		viewModel.apply {
			getNetworkResults().observe(this@ArticlesCategoryFragment, Observer { adapterArticlesCategory.setData(it) })
			getArticlesFromCategory(category.toLowerCase())
		}
	}

	private fun setupRecyclerView() =
		rvArticlesFromCategory.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = adapterArticlesCategory
		}

	override fun onItemClick(dataItem: Article) {}
}
