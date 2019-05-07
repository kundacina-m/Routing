package com.example.topnews.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.topnews.data.db.Article

@Entity(
	primaryKeys = ["tagName", "articleId"],
	foreignKeys = [

		ForeignKey(
			entity = Tag::class,
			parentColumns = arrayOf("name"),
			childColumns = arrayOf("tagName"),
			onDelete = CASCADE
		),

		ForeignKey(
			entity = Article::class,
			parentColumns = arrayOf("publishedAt"),
			childColumns = arrayOf("articleId"),
			onDelete = CASCADE
		)
	]
)
data class TagArticle(

	@ColumnInfo(name = "tagName")
	val tagName: String,

	@ColumnInfo(name = "articleId")
	val articleId: String

)
