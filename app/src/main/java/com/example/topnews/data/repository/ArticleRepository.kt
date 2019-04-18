package com.example.topnews.data.repository

import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.ArticleLocalStorage
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.screens.search.pageSize
import io.reactivex.Flowable
import io.reactivex.Single

class ArticleRepository {

	private val localStorage:ArticleLocalStorage by lazy {
		App.injectLocalStorage()
	}

	private val remoteStorage by lazy {
		App.injectRemoteStorage()
	}

	fun addLocal(item: Article): Flowable<WrappedResponse<Boolean>> {
		return localStorage.add(item)
	}

	fun removeLocal(item: Article): Flowable<WrappedResponse<Boolean>> {
		return localStorage.remove(item)
	}

	fun getAllLocal(): Flowable<WrappedResponse<ArrayList<Article>>> {
		return localStorage.getAll()
	}

	fun getAllRemote(): Single<WrappedResponse<List<Article>>> {
		return remoteStorage.getAll()
	}

	fun getArticlesPagination(
		from: Int,
		to: Int
	): Flowable<WrappedResponse<ArrayList<Article>>> {
		return localStorage.getItemsFromTo(from, to)
	}

	fun getArticlesByPages(query: String, pages: Int): Single<WrappedResponse<List<Article>>> {
		return remoteStorage.getItemsByQuery(
			mapOf(
				"q" to query, "page" to pages.toString(), "pageSize" to pageSize
				.toString()
			)
		)
	}

	fun getArticlesByCategory(category: String): Single<WrappedResponse<List<Article>>> {
		return remoteStorage.getItemsByQuery(mapOf("category" to category))
	}

	fun checkIfArticleExistsInDB(article: Article): Flowable<Boolean> {
		return localStorage.isItemInDB(article)
	}

}