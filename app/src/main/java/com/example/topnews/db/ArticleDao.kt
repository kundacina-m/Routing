package com.example.topnews.db

import com.example.topnews.screens.Article

interface ArticleDao : BaseDao<Article> {

	fun getArticlesFromTo(from: Int, to: Int): Execute<ArrayList<Article>>
	fun checkIfArticleExists(article: Article): Execute<Boolean>
}