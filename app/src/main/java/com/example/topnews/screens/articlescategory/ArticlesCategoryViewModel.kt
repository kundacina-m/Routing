package com.example.topnews.screens.articlescategory

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.data.Article
import com.example.topnews.data.repository.ArticleRepository
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ArticlesCategoryViewModel @Inject constructor(val repository: ArticleRepository) :
	BaseViewModel() {

	val articles = MutableLiveData<List<Article>>()

	init {
		disposables.add(
			repository.getArticlesByCategory("general", 1, 20)
				.subscribeBy {
					articles.postValue(it)
				})
	}

}