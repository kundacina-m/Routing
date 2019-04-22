package com.example.topnews.data.repository

import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.data.model.ArticleRaw
import com.example.topnews.domain.ArticleRemoteStorage
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.utils.toSealed
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ArticleRemoteStorageImpl : ArticleRemoteStorage {

	private val api by lazy {
		App.injectApi()
	}

	override fun getAll(): Single<WrappedResponse<List<Article>>> {

		return api.getArticles()
			.map { it.articles }
			.flatMap { task ->
				Observable.fromIterable(task).map(this::fromRawToObj).toList()
			}
			.subscribeOn(Schedulers.io())
			.toSealed()
	}

	override fun getItemsByQuery(queryParams: Map<String, String>): Single<WrappedResponse<List<Article>>> {

		return api.getItemsByQuery(queryParams)
			.map { it.articles }
			.flattenAsObservable {list-> list}
			.map(this::fromRawToObj)
			.toList()
			.subscribeOn(Schedulers.io())
			.toSealed()
	}

	private fun fromRawToObj(raw: ArticleRaw): Article {
		return Article(publishedAt = raw.publishedAt!!).apply {
			this.author = raw.author
			this.content = raw.content
			this.description = raw.description
			this.source = raw.sourceRaw?.name
			this.title = raw.title
			this.url = raw.url
			this.urlToImage = raw.urlToImage
		}
	}

}
