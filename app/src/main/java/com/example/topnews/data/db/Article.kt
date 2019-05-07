package com.example.topnews.data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Entity(tableName = "articles")
@Parcelize
data class Article(

	@ColumnInfo(name = "source")
	var source: String? = null,

	@ColumnInfo(name = "author")
	var author: String? = null,

	@ColumnInfo(name = "title")
	var title: String? = null,

	@ColumnInfo(name = "description")
	var description: String? = null,

	@ColumnInfo(name = "url")
	var url: String? = null,

	@ColumnInfo(name = "urlToImg")
	var urlToImage: String? = null,

	@PrimaryKey
	@ColumnInfo(name = "publishedAt")
	var publishedAt: Timestamp,

	@ColumnInfo(name = "content")
	var content: String? = null
) : Parcelable