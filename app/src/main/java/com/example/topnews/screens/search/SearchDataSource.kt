package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import base.BaseDataSource
import com.example.topnews.data.db.Article
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants.PAGE_SIZE_SEARCH
import io.reactivex.rxkotlin.subscribeBy

class SearchDataSource(
	private val onError: MutableLiveData<OnError<Nothing>>,
	val query: String,
	val repository: ArticleRepository
) : BaseDataSource<Article>() {

	override fun initialLoad(callback: LoadInitialCallback<Int, Article>) {
		if (query.isNotEmpty())
			disposables.add(repository.getByQueryRemote(query, currentPage, PAGE_SIZE_SEARCH)
				.subscribeBy {
					if (it is OnSuccess)
						callback.onResult(it.item, null, nextPage)
					else onError.postValue(it as OnError<Nothing>)
				})
	}

	override fun onScrollLoad(callback: LoadCallback<Int, Article>) {
		if (query.isNotEmpty())
			disposables.add(repository.getByQueryRemote(query, currentPage, PAGE_SIZE_SEARCH)
				.subscribeBy {
					if (it is OnSuccess)
						callback.onResult(it.item, nextPage)
					else onError.postValue(it as OnError<Nothing>)
				})
	}

}