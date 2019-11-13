package com.example.topnews.data.repository

import com.example.topnews.data.fromRawToObj
import com.example.topnews.data.networking.ArticleApi
import io.reactivex.schedulers.Schedulers

class ArticleRemoteStorageImpl(private val api: ArticleApi) {

	fun getItemsByQuery(queryParams: Map<String, String>) =

		api.getItemsByQuery(queryParams)
			.map { it.articles }
			.flattenAsObservable { list -> list }
			.map { it.fromRawToObj() }
			.toList()
			.subscribeOn(Schedulers.io())
}
