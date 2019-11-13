package com.example.topnews.data

import android.os.Parcelable
import com.example.topnews.data.networking.responses.ArticleRaw
import com.example.topnews.utils.fromStringISOtoDate
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Article(
	var source: String? = null,
	var author: String? = null,
	var title: String? = null,
	var description: String? = null,
	var url: String,
	var urlToImage: String? = null,
	var publishedAt: Date? = null,
	var content: String? = null
) : Parcelable

@Parcelize
data class Source(
	var id: String? = null,
	var name: String
) : Parcelable


fun ArticleRaw.fromRawToObj() =
	Article(
		url = this.url!!,
		author = this.author,
		content = this.content,
		description = this.description,
		source = this.source?.name,
		title = this.title,
		publishedAt = this.publishedAt!!.fromStringISOtoDate(),
		urlToImage = this.urlToImage
	)
