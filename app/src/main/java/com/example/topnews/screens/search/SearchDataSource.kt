package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource.InvalidatedCallback
import androidx.paging.PageKeyedDataSource
import androidx.paging.PageKeyedDataSource.LoadCallback
import androidx.paging.PageKeyedDataSource.LoadInitialCallback
import androidx.paging.PageKeyedDataSource.LoadInitialParams
import androidx.paging.PageKeyedDataSource.LoadParams
import com.example.topnews.data.db.Article
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class SearchDataSource(
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

	// Added for clearing disposables
	override fun addInvalidatedCallback(onInvalidatedCallback: InvalidatedCallback) {
		super.addInvalidatedCallback(onInvalidatedCallback)
		disposables.clear()
	}

}