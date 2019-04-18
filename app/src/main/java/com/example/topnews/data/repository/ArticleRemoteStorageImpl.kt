package com.example.topnews.data.repository

import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.ArticleRemoteStorage
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.utils.toSealed
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ArticleRemoteStorageImpl : ArticleRemoteStorage {

	private val api by lazy {
		App.injectApi()
	}

	override fun getAll(): Single<WrappedResponse<List<Article>>> {

		return api.getArticles()
			.map { it.articles }
			.subscribeOn(Schedulers.io())
			.toSealed()

	}

	override fun getItemsByQuery(queryParams: Map<String, String>): Single<WrappedResponse<List<Article>>> {

		return api.getItemsByQuery(queryParams)
			.map { it.articles }
			.subscribeOn(Schedulers.io())
			.toSealed()
	}

}
