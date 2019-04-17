package com.example.topnews.domain

import com.example.topnews.data.db.ArticleDaoImpl
import com.example.topnews.data.model.Article
import com.example.topnews.domain.RequestError.UnknownError
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.domain.crud.LocalCRUD
import io.reactivex.Flowable

class ArticleLocalStorageImpl : LocalCRUD<Article> {

	private val articleDao by lazy {
		ArticleDaoImpl()
	}

	override fun add(item: Article): Flowable<WrappedResponse<Boolean>> {
		return articleDao.addItem(item)
	}

	override fun remove(item: Article): Flowable<WrappedResponse<Boolean>> {
		return articleDao.deleteItem(item)
	}

	override fun get(id: String): Flowable<WrappedResponse<Article>> {
		TODO()
	}

	override fun getAll(): Flowable<WrappedResponse<ArrayList<Article>>> {
		return articleDao.getAllItems()
	}

	fun getItemsFromTo(from: Int, to: Int) : Flowable<WrappedResponse<ArrayList<Article>>>{
		return articleDao.getArticlesFromTo(from,to)
	}

	fun isItemInDB(item: Article) : Flowable<Boolean> {
		return articleDao.checkIfArticleExists(item)
	}
}

fun <T> Flowable<T>.toSealed(): Flowable<WrappedResponse<T>> {

	return this.map<WrappedResponse<T>> { OnSuccess(it) }
		.onErrorReturn {
			OnError(UnknownError)
		}
}