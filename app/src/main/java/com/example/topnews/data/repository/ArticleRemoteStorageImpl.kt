package com.example.topnews.data.repository

import com.example.topnews.data.db.Article
import com.example.topnews.data.networking.ArticleApi
import com.example.topnews.domain.ArticleRemoteStorage
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.utils.ArticleConverter
import com.example.topnews.utils.toSealed
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ArticleRemoteStorageImpl(private val api: ArticleApi) : ArticleRemoteStorage {

	override fun getAll(): Single<WrappedResponse<List<Article>>> {

		return api.getArticles()
			.map { it.articles }
			.flatMap { task ->
				Observable.fromIterable(task).map(ArticleConverter::fromRawToObj).toList()
			}
			.subscribeOn(Schedulers.io())
			.toSealed()
	}

	override fun getItemsByQuery(queryParams: Map<String, String>): Single<WrappedResponse<List<Article>>> {

		return api.getItemsByQuery(queryParams)
			.map { it.articles }
			.flattenAsObservable { list -> list }
			.map(ArticleConverter::fromRawToObj)
			.toList()
			.subscribeOn(Schedulers.io())
			.toSealed()
	}

}
