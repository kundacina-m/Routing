package com.example.topnews.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagArticle(

	@ColumnInfo(name = "tagName")
	val tagName: String,

	@ColumnInfo(name = "articleId")
	val articleId: String,

	@PrimaryKey(autoGenerate = true)
	val id: Long = 0
)
