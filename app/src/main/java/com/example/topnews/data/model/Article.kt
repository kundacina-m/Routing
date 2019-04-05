package com.example.topnews.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


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