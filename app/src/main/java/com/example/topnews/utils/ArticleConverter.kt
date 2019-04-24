package com.example.topnews.utils

import com.example.topnews.data.model.Article
import com.example.topnews.data.model.ArticleRaw
import com.example.topnews.data.model.SourceRaw

object ArticleConverter {

	fun fromRawToObj(raw: ArticleRaw): Article {
		return Article(publishedAt = raw.publishedAt!!).apply {
			this.author = raw.author
			this.content = raw.content
			this.description = raw.description
			this.source = raw.sourceRaw?.name
			this.title = raw.title
			this.url = raw.url
			this.urlToImage = raw.urlToImage
		}
	}

	fun fromObjToRaw(obj: Article): ArticleRaw {
		return ArticleRaw().apply {
			this.publishedAt = obj.publishedAt
			this.author = obj.author
			this.content = obj.content
			this.description = obj.description
			this.sourceRaw = SourceRaw(null, obj.source!!)
			this.title = obj.title
			this.url = obj.url
			this.urlToImage = obj.urlToImage
		}
	}
}