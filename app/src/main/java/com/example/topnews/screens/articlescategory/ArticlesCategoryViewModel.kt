package com.example.topnews.screens.articlescategory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.data.model.Article
import com.example.topnews.App
import com.example.topnews.domain.WrappedResponse.OnSuccess

class ArticlesCategoryViewModel : ViewModel() {

	private val repository = App.injectRepository()

	private var articles = MutableLiveData<List<Article>>()
	fun getNetworkResults() = articles

	fun getArticlesFromCategory(category: String) {
		repository.getArticlesByCategory(category) {
			if (it is OnSuccess)
			articles.postValue(it.item)
		}
	}
}