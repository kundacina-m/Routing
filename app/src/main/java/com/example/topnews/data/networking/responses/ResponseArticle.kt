package com.example.topnews.data.networking.responses

import com.example.topnews.data.model.Article
import com.google.gson.annotations.SerializedName

data class ResponseArticle(
	@SerializedName("status") val status: String,
	@SerializedName("totalResults")	val totalResults: Int,
	@SerializedName("articles")	val articles: List<Article>
)