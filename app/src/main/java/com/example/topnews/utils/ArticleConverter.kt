package com.example.topnews.utils

import com.example.topnews.data.db.Article
import com.example.topnews.data.db.Source
import com.example.topnews.data.model.ArticleRaw
import com.example.topnews.utils.Constants.DATE_ONLY
import java.time.Instant
import java.util.Date

object ArticleConverter {

	fun fromRawToObj(raw: ArticleRaw): Article {

		val what = raw.publishedAt!!.fromStringISOtoDate()

		return Article(publishedAt = what).apply {
			this.author = raw.author
			this.content = raw.content
			this.description = raw.description
			this.source = raw.source?.name
			this.title = raw.title
			this.url = raw.url
			this.urlToImage = raw.urlToImage
		}
	}

//	fun fromObjToRaw(obj: Article): ArticleRaw {
//		return ArticleRaw().apply {
//			this.publishedAt = obj.publishedAt
//			this.author = obj.author
//			this.content = obj.content
//			this.description = obj.description
//			this.source = Source(null, obj.source!!)
//			this.title = obj.title
//			this.url = obj.url
//			this.urlToImage = obj.urlToImage
//		}
//	}
}