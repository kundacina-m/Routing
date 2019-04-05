package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.RequestError
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess

class TopNewsViewModel : ViewModel() {

	private val repository = App.injectRepository()

	private var articles = MutableLiveData<List<Article>>()
	private var error = MutableLiveData<RequestError>()

	fun getNetworkResults() = articles

	fun getArticles() {
		repository.getAllRemote {
			when (it) {
				is OnSuccess -> articles.postValue(it.item)
				is OnError -> error.postValue(it.error)
			}
		}
	}
}