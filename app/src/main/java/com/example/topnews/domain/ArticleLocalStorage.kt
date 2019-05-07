package com.example.topnews.domain

import com.example.topnews.data.db.Article
import com.example.topnews.domain.crud.LocalCRUD
import io.reactivex.Flowable

interface ArticleLocalStorage : LocalCRUD<Article> {
	override fun get(id: String): Flowable<WrappedResponse<Article>> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	fun getItemsFromTo(from: Int, to: Int): Flowable<WrappedResponse<ArrayList<Article>>>
	fun isItemInDB(item: Article): Flowable<Boolean>

}