package com.example.topnews.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class TagWithArticles (
	@Embedded val tag: Tag,
	@Relation(
		parentColumn = "name",
		entityColumn = "tagName",
		entity = TagArticle::class,
		projection = ["articleId"]
	) val articleIdList: List<String>
)

data class ArticlesWithTag (
	@Embedded val article: Article,
	@Relation(
		parentColumn = "publishedAt",
		entityColumn = "articleId",
		entity = TagArticle::class,
		projection = ["tagName"]
	) val tagIdList: List<String>
)