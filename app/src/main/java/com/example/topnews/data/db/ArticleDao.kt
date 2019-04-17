package com.example.topnews.data.db

import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse
import io.reactivex.Flowable

interface ArticleDao : BaseDao<Article> {

	fun getArticlesFromTo(from: Int, to: Int): Flowable<WrappedResponse<ArrayList<Article>>>
	fun checkIfArticleExists(article: Article): Flowable<Boolean>
}