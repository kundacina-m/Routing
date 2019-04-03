package com.example.topnews.screens

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseModel(
	@SerializedName("status")
	val status: String,
	@SerializedName("totalResults")
	val totalResults: Int,
	@SerializedName("articles")
	val articles: List<Article>
)

@Parcelize
data class Article(
	val source: HashMap<String, String>,
	val author: String,
	val title: String,
	val description: String,
	val url: String,
	val urlToImage: String,
	val publishedAt: String,
	val content: String
) : Parcelable