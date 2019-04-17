package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.RequestError
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.rxkotlin.subscribeBy

class TopNewsViewModel : BaseViewModel() {

	private val repository = App.injectRepository()

	private var articles = MutableLiveData<List<Article>>()
	private var error = MutableLiveData<RequestError>()

	fun getNetworkResults() = articles

	fun getArticles() =
		disposables.add(repository.getAllRemote()
			.subscribeBy {
				when (it) {
					is OnSuccess -> articles.postValue(it.item)
					is OnError -> error.postValue(it.error)
				}
			})
}





