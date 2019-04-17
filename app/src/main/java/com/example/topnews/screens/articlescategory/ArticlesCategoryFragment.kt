package com.example.topnews.screens.articlescategory

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseAdapter
import base.BaseFragment
import com.example.topnews.R
import com.example.topnews.data.model.Article
import com.example.topnews.domain.RequestError
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
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
			getNetworkResults().observe(this@ArticlesCategoryFragment, Observer {
				if (it is OnSuccess) adapterArticlesCategory.setData(it.item) else handleError(it as OnError)
			})
			getArticlesFromCategory(category.toLowerCase())
		}
	}

	private fun setupRecyclerView() =
		rvArticlesFromCategory.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = adapterArticlesCategory
		}

	override fun onItemClick(dataItem: Article) {}

	private fun handleError(onError: OnError<List<Article>>) =
		when (onError.error) {
			is RequestError.UnknownError -> Log.d(TAG, "handleError: Unknown ")
			is RequestError.HttpError -> Log.d(TAG, "handleError: Http")
			is RequestError.NoInternetError -> Log.d(TAG, "handleError: No Internet")
			is RequestError.ServerError -> Log.d(TAG, "handleError: Server")
		}

}
