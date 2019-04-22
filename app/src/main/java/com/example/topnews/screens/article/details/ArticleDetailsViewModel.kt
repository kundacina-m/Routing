package com.example.topnews.screens.article.details

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ArticleDetailsViewModel : BaseViewModel() {

	private val repository = App.injectRepository()

	var isInDb = MutableLiveData<Boolean>()

	fun insertArticle(article: Article) {

		disposables.add(
			Single.just(repository)
				.subscribeOn(Schedulers.io())
				.subscribeBy {
					it.addLocal(item = article).subscribeBy { returnVal ->
						if (returnVal is OnSuccess)
							isInDb.postValue(true)
						else isInDb.postValue(false)
					}
				})
	}

	fun removeArticle(article: Article) {

		disposables.add(
			Single.just(repository)
				.subscribeOn(Schedulers.io())
				.subscribeBy {
					it.removeLocal(item = article).subscribeBy { returnVal ->
						if (returnVal is OnSuccess)
							isInDb.postValue(false)
						else isInDb.postValue(true)
					}
				})
	}

	fun checkIfArticleExists(article: Article) =
		disposables.add(
			repository.checkIfArticleExistsInDB(article).subscribeBy {
				if (it is OnSuccess)
					isInDb.postValue(true)
				else isInDb.postValue(false)
			})

}