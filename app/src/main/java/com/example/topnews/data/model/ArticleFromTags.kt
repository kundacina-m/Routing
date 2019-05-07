package com.example.topnews.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.topnews.data.db.Article

class TagWithArticlesOld {
	@Embedded lateinit var tag: Tag
	@Relation(
		parentColumn = "name",
		entityColumn = "tagName",
		entity = TagArticle::class
	)
	var articleIdList: List<Article>? = null
}

data class ArticlesWithTag(
	@Embedded val article: Article,
	@Relation(
		parentColumn = "publishedAt",
		entityColumn = "articleId",
		entity = TagArticle::class,
		projection = ["tagName"]
	) val tagIdList: List<String>
)