package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import base.BaseDataSource
import com.example.topnews.App
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.rxkotlin.subscribeBy

class SearchDataSource(
	private val onError: MutableLiveData<OnError<Nothing>>,
	val query: String
) : BaseDataSource<Article>() {

	override fun initialLoad(callback: LoadInitialCallback<Int, Article>) {
		if (query != "")
			disposables.add(App.injectRepository().getByQueryRemote(query, currentPage).subscribeBy {
				if (it is OnSuccess)
					callback.onResult(it.item, null, nextPage)
				else onError.postValue(it as OnError<Nothing>)
			})
	}

	override fun onScrollLoad(callback: LoadCallback<Int, Article>) {
		if (query != "")
			disposables.add(App.injectRepository().getByQueryRemote(query, currentPage).subscribeBy {
				if (it is OnSuccess)
					callback.onResult(it.item, nextPage)
				else onError.postValue(it as OnError<Nothing>)
			})
	}

}