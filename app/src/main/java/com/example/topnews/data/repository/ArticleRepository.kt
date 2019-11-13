package com.example.topnews.data.repository

import com.example.topnews.data.Article
import com.example.topnews.utils.Constants.API_CATEGORY
import com.example.topnews.utils.Constants.API_PAGE
import com.example.topnews.utils.Constants.API_PAGE_SIZE
import io.reactivex.Single
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val remoteStorage: ArticleRemoteStorageImpl) {

	fun getArticlesByCategory(
		category: String,
		pageNum: Int,
		pageSize: Int
	): Single<List<Article>> =
		remoteStorage.getItemsByQuery(
			mapOf(
				API_PAGE to pageNum.toString(),
				API_PAGE_SIZE to pageSize.toString(),
				API_CATEGORY to category
			)
		)

}