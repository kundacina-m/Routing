package com.example.topnews.data.repository

import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.ArticleRemoteStorage
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.screens.search.pageSize
import com.example.topnews.utils.Constants.ARG_CATEGORY
import com.example.topnews.utils.toSealed
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleRepository {

	private val articleDao by lazy {
		App.injectArticleDao()
	}

	private val remoteStorage: ArticleRemoteStorage by lazy {
		App.injectRemoteStorage()
	}

	fun addLocal(item: Article): Single<WrappedResponse<Long>> {
		return Single.just(articleDao.addItem(item)).toSealed()
	}

	fun removeLocal(item: Article): Single<WrappedResponse<Int>> {
		return Single.just(articleDao.deleteItem(item)).toSealed()
	}

	fun getAllLocal(): Flowable<WrappedResponse<List<Article>>> {
		return articleDao.getAllItems().toSealed()
	}

	fun getAllRemote(): Single<WrappedResponse<List<Article>>> {
		return remoteStorage.getAll()
	}

	fun getArticlesByPages(query: String, pages: Int): Single<WrappedResponse<List<Article>>> {
		return remoteStorage.getItemsByQuery(
			mapOf(
				"q" to query,
				"page" to pages.toString(),
				"pageSize" to pageSize.toString()
			)
		)
	}

	fun getArticlesByCategory(category: String): Single<WrappedResponse<List<Article>>> {
		return remoteStorage.getItemsByQuery(mapOf(ARG_CATEGORY to category))
	}

	fun checkIfArticleExistsInDB(article: Article): Single<WrappedResponse<Article>> {
		return articleDao.getItem(article.publishedAt)
			.subscribeOn(Schedulers.io())
			.toSealed()
	}
}