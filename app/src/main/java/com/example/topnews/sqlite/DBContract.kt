package com.example.topnews.sqlite

import android.provider.BaseColumns

object DBContract {

	class ArticleEntry : BaseColumns {
		companion object {
			const val TABLE_NAME = "readlater"
			const val COLUMN_ID = "id"
			const val COLUMN_SOURCE = "source"
			const val COLUMN_AUTHOR = "author"
			const val COLUMN_TITLE = "title"
			const val COLUMN_DESCRIPTION = "description"
			const val COLUMN_URL = "url"
			const val COLUMN_URL_TO_IMAGE = "urltoimage"
			const val COLUMN_PUBLISHED_AT = "publishedat"
			const val COLUMN_CONTENT = "content"
		}
	}
}