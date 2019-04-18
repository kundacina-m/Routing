package com.example.topnews.data.repository

import com.example.topnews.data.db.ArticleDaoImpl
import com.example.topnews.data.model.Article
import com.example.topnews.domain.ArticleLocalStorage
import com.example.topnews.domain.WrappedResponse
import io.reactivex.Flowable

class ArticleLocalStorageImpl : ArticleLocalStorage {

	private val articleDao by lazy {
		ArticleDaoImpl()
	}

	override fun add(item: Article): Flowable<WrappedResponse<Boolean>> {
		return articleDao.addItem(item)
	}

	override fun remove(item: Article): Flowable<WrappedResponse<Boolean>> {
		return articleDao.deleteItem(item)
	}

	override fun getAll(): Flowable<WrappedResponse<ArrayList<Article>>> {
		return articleDao.getAllItems()
	}

	override fun getItemsFromTo(from: Int, to: Int): Flowable<WrappedResponse<ArrayList<Article>>> {
		return articleDao.getArticlesFromTo(from, to)
	}

	override fun isItemInDB(item: Article): Flowable<Boolean> {
		return articleDao.checkIfArticleExists(item)
	}
}