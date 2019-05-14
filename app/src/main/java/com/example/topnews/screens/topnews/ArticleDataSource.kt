package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.topnews.data.db.Article
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class ArticleDataSource(
	val repository: ArticleRepository,
	private val onError: MutableLiveData<OnError<Nothing>>
) : PageKeyedDataSource<Int, Article>() {

	private val disposables by lazy {
		CompositeDisposable()
	}

	override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {

		val currentPage = 1
		val nextPage = currentPage + 1

		disposables.add(repository.getAllRemote(currentPage).subscribeBy {
			if (it is OnSuccess)
				callback.onResult(it.item, null, nextPage)
			else onError.postValue(it as OnError<Nothing>)
		})

	}

	override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

		val currentPage = params.key
		val nextPage = currentPage + 1

		disposables.add(repository.getAllRemote(currentPage).subscribeBy {
			callback.onResult((it as OnSuccess).item, nextPage)
		})

	}

	override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {}

}