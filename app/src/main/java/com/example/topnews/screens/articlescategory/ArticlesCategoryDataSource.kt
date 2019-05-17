package com.example.topnews.screens.articlescategory

import androidx.lifecycle.MutableLiveData
import base.BaseDataSource
import com.example.topnews.App
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.rxkotlin.subscribeBy

class ArticlesCategoryDataSource(
	private val category: String,
	val onError: MutableLiveData<OnError<Nothing>>
) : BaseDataSource<Article>() {

	override fun initialLoad(callback: LoadInitialCallback<Int, Article>) {
		disposables.add(App.injectRepository().getArticlesByCategory(category).subscribeBy {
			if (it is OnSuccess) {
				callback.onResult(it.item, null, nextPage)
			} else onError.postValue(it as OnError<Nothing>)
		})
	}

	override fun onScrollLoad(callback: LoadCallback<Int, Article>) {
		disposables.add(App.injectRepository().getArticlesByCategory(category).subscribeBy {
			if (it is OnSuccess) {
				callback.onResult(it.item, nextPage)
			} else onError.postValue(it as OnError<Nothing>)
		})
	}
}