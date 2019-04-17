package com.example.topnews.domain

import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.domain.crud.RemoteCRUD
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ArticleRemoteStorageImpl : RemoteCRUD<Article> {

	private val api by lazy {
		App.injectApi()
	}

	override fun add(item: Article): Single<WrappedResponse<Boolean>> {
		TODO()
	}

	override fun remove(item: Article): Single<WrappedResponse<Boolean>> {
		TODO()
	}

	override fun get(id: String): Single<WrappedResponse<Article>> {
		TODO()
	}

	override fun getAll(): Single<WrappedResponse<List<Article>>> {

		return api.getArticles()
			.map {
				it.articles
			}
			.subscribeOn(Schedulers.io())
			.toSealed()

	}

	override fun getItemsByQuery(queryParams: Map<String, String>): Single<WrappedResponse<List<Article>>> {

		return api.getItemsByQuery(queryParams)
			.map {
				it.articles
			}
			.subscribeOn(Schedulers.io())
			.toSealed()
	}

}

fun <T> Single<T>.toSealed(): Single<WrappedResponse<T>> {

	return this.map<WrappedResponse<T>> { OnSuccess(it) }
		.onErrorReturn {
			OnError(RequestError.UnknownError)
		}
}