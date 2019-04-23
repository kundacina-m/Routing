package com.example.topnews.data.repository

import com.example.topnews.data.db.ArticleDao
import com.example.topnews.data.model.Article
import com.example.topnews.domain.ArticleRemoteStorage
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.screens.search.pageSize
import com.example.topnews.utils.Constants.API_CATEGORY
import com.example.topnews.utils.Constants.API_PAGE
import com.example.topnews.utils.Constants.API_PAGESIZE
import com.example.topnews.utils.Constants.API_QUERY
import com.example.topnews.utils.toSealed
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ArticleRepository(
	private val articleDao: ArticleDao,
	private val remoteStorage: ArticleRemoteStorage
) {

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
				API_QUERY to query,
				API_PAGE to pages.toString(),
				API_PAGESIZE to pageSize.toString()
			)
		)
	}

	fun getArticlesByCategory(category: String): Single<WrappedResponse<List<Article>>> {
		return remoteStorage.getItemsByQuery(mapOf(API_CATEGORY to category))
	}

	fun checkIfArticleExistsInDB(article: Article): Single<WrappedResponse<Article>> {
		return articleDao.getItem(article.publishedAt).toSealed()
	}
}