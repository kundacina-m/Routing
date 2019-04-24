package com.example.topnews.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagArticle(
	@PrimaryKey(autoGenerate = true)
	val id: Long,
	val tagName: String,
	val articleId: String
)
