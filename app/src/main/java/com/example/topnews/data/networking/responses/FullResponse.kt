package com.example.topnews.data.networking.responses

import com.google.gson.annotations.SerializedName

data class FullResponse(
	@SerializedName("status") val status: String,
	@SerializedName("totalResults") val totalResults: Int,
	@SerializedName("articles") val articles: List<ArticleRaw>
)