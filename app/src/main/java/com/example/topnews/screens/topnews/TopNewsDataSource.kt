package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import base.BaseDataSource
import com.example.topnews.data.db.Article
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants.PAGE_SIZE_TOP_NEWS
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit.SECONDS

class TopNewsDataSource(
	private val onError: MutableLiveData<OnError<Nothing>>,
	private val loading: MutableLiveData<Boolean>,
	val repository: ArticleRepository

) : BaseDataSource<Article>() {

	override fun initialLoad(callback: LoadInitialCallback<Int, Article>) {
		showLoading(true)
		disposables.add(repository.getAllRemote(currentPage, PAGE_SIZE_TOP_NEWS)
			.delay(1, SECONDS)
			.subscribeBy {
				if (it is OnSuccess)
					callback.onResult(it.item, null, nextPage)
				else onError.postValue(it as OnError<Nothing>)
				showLoading(false)
			})
	}

	override fun onScrollLoad(callback: LoadCallback<Int, Article>) {
		showLoading(true)
		disposables.add(repository.getAllRemote(currentPage, PAGE_SIZE_TOP_NEWS)
			.delay(1, SECONDS)
			.subscribeBy {
				if (it is OnSuccess)
					callback.onResult(it.item, nextPage)
				else onError.postValue(it as OnError<Nothing>)
				showLoading(false)
			})
	}

	private fun showLoading(show: Boolean) =
		loading.postValue(show)
}