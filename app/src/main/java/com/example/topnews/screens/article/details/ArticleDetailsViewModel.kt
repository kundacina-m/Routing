package com.example.topnews.screens.article.details

import androidx.lifecycle.MutableLiveData

import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.rxkotlin.subscribeBy

class ArticleDetailsViewModel : BaseViewModel() {

	private val repository = App.injectRepository()

	var isInDb = MutableLiveData<Boolean>()

	fun insertArticle(article: Article) =
		disposables.add(repository.addLocal(article).subscribeBy {
			if (it is OnSuccess) {
				isInDb.postValue(it.item)
			}
		})

	fun removeArticle(article: Article) =
		disposables.add(repository.removeLocal(article).subscribeBy {
			if (it is OnSuccess) {
				isInDb.postValue(!it.item)
			}
		})

	fun checkIfArticleExists(article: Article) =
		disposables.add(
			repository.checkIfArticleExistsInDB(article).subscribeBy {
				isInDb.postValue(it)
			})

}