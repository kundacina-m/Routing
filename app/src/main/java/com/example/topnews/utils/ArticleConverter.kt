package com.example.topnews.utils

import com.example.topnews.data.db.Article
import com.example.topnews.data.model.ArticleRaw

object ArticleConverter {

	fun fromRawToObj(raw: ArticleRaw): Article {

		return Article(url = raw.url!!).apply {
			this.author = raw.author
			this.content = raw.content
			this.description = raw.description
			this.source = raw.source?.name
			this.title = raw.title
			this.publishedAt = raw.publishedAt!!.fromStringISOtoDate()
			this.urlToImage = raw.urlToImage
		}
	}

}