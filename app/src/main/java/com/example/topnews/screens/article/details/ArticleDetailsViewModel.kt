package com.example.topnews.screens.article.details

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.data.db.Article
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleDetailsViewModel @Inject constructor(val repository: ArticleRepository) : BaseViewModel() {

	var isInDb = MutableLiveData<Boolean>()

	fun insertArticle(article: Article) =
		disposables.add(
			Single.just(repository)
				.subscribeOn(Schedulers.io())
				.subscribeBy {
					it.addToDB(item = article).subscribeBy { returnVal ->
						if (returnVal is OnSuccess)
							isInDb.postValue(true)
						else isInDb.postValue(false)
					}
				})

	fun removeArticle(article: Article) =
		disposables.add(
			Single.just(repository)
				.subscribeOn(Schedulers.io())
				.subscribeBy {
					it.removeFromDB(item = article).subscribeBy { returnVal ->
						if (returnVal is OnSuccess)
							isInDb.postValue(false)
						else isInDb.postValue(true)
					}
				})

	fun checkIfArticleExists(article: Article) =
		disposables.add(
			repository.checkIfArticleExistsInDB(article)
				.subscribeOn(Schedulers.io())
				.subscribeBy {
					if (it is OnSuccess)
						isInDb.postValue(true)
					else isInDb.postValue(false)
				})

	fun addTagToArticle(articleId: String, tag: String) {
		repository.addTag(articleId, tag)
	}

	fun removeTagArticle(articleId: String) {
		repository.removeTagArticle(articleId)

	}
}