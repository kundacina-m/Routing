package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse
import io.reactivex.rxkotlin.subscribeBy

class TopNewsViewModel : BaseViewModel() {

	private val repository = App.injectRepository()
	private var articles = MutableLiveData<WrappedResponse<List<Article>>>()

	fun getNetworkResults() = articles

	fun getArticles() =
		disposables.add(repository.getAllRemote()
			.subscribeBy {
				articles.postValue(it)
			})
}





