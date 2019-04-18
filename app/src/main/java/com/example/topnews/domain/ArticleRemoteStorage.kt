package com.example.topnews.domain

import com.example.topnews.data.model.Article
import com.example.topnews.domain.crud.RemoteCRUD
import io.reactivex.Single

interface ArticleRemoteStorage : RemoteCRUD<Article> {
	override fun add(item: Article): Single<WrappedResponse<Boolean>> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun get(id: String): Single<WrappedResponse<Article>> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun remove(item: Article): Single<WrappedResponse<Boolean>> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}