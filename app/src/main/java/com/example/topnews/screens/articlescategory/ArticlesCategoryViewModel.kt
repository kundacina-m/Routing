package com.example.topnews.screens.articlescategory

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse
import io.reactivex.rxkotlin.subscribeBy

class ArticlesCategoryViewModel : BaseViewModel() {

	private val repository = App.injectRepository()

	private var articles = MutableLiveData<WrappedResponse<List<Article>>>()
	fun getNetworkResults() = articles

	fun getArticlesFromCategory(category: String) =
		disposables.add(
			repository.getArticlesByCategory(category)
				.subscribeBy {
					articles.postValue(it)
				})

}