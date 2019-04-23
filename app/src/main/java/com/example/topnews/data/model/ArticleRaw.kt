package com.example.topnews.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleRaw(

	@SerializedName("source")
	var sourceRaw: SourceRaw? = null,

	@SerializedName("author")
	var author: String? = null,

	@SerializedName("title")
	var title: String? = null,

	@SerializedName("description")
	var description: String? = null,

	@SerializedName("url")
	var url: String? = null,

	@SerializedName("urlToImage")
	var urlToImage: String? = null,

	@SerializedName("publishedAt")
	var publishedAt: String? = null,

	@SerializedName("content")
	var content: String? = null
) : Parcelable




