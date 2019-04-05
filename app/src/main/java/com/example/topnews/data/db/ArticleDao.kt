package com.example.topnews.data.db

import com.example.topnews.data.model.Article

interface ArticleDao : BaseDao<Article> {

	fun getArticlesFromTo(from: Int, to: Int): Execute<ArrayList<Article>>
	fun checkIfArticleExists(article: Article): Execute<Boolean>
}