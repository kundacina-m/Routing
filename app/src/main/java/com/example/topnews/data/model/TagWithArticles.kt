package com.example.topnews.data.model

data class TagWithArticles(
	val name: String,
	val articles : List<Article>
)