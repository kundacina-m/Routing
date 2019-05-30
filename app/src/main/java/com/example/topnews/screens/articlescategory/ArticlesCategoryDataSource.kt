package com.example.topnews.screens.articlescategory

import androidx.lifecycle.MutableLiveData
import base.BaseDataSource
import com.example.topnews.data.db.Article
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants.PAGE_SIZE_CATEGORIES
import io.reactivex.rxkotlin.subscribeBy

class ArticlesCategoryDataSource(
	private val category: String,
	val onError: MutableLiveData<OnError<Nothing>>,
	val repository: ArticleRepository
) : BaseDataSource<Article>() {

	override fun initialLoad(callback: LoadInitialCallback<Int, Article>) {
		disposables.add(
			repository.getArticlesByCategory(category, currentPage, PAGE_SIZE_CATEGORIES)
				.subscribeBy {
					if (it is OnSuccess) {
						callback.onResult(it.item, null, nextPage)
					} else onError.postValue(it as OnError<Nothing>)
				})
	}

	override fun onScrollLoad(callback: LoadCallback<Int, Article>) {
		disposables.add(
			repository.getArticlesByCategory(category, currentPage, PAGE_SIZE_CATEGORIES)
				.subscribeBy {
					if (it is OnSuccess) {
						callback.onResult(it.item, nextPage)
					} else onError.postValue(it as OnError<Nothing>)
				})
	}
}