package com.example.topnews.db

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import com.example.topnews.screens.Article
import com.example.topnews.sqlite.DBContract
import com.example.topnews.sqlite.DBHelper
import com.example.topnews.sqlite.DBHelper.Companion.SQL_CREATE_ENTRIES
import com.example.topnews.utils.App
import com.example.topnews.utils.Constants
import com.example.topnews.utils.Constants.ARTICLE_NO_AUTHOR
import com.example.topnews.utils.Constants.ARTICLE_NO_CONTENT

class ArticleDaoImpl : ArticleDao {

	private var database: DBHelper = App.injectDB()

	protected fun addItem(item: Article): Boolean {
		if (checkIfRowAlreadyExists(item)) return false

		val db = database.writableDatabase
		db.insert(DBContract.ArticleEntry.TABLE_NAME, null, getRowValues(item))

		return true
	}

	protected fun deleteItem(item: Article): Boolean {
		val db = database.writableDatabase

		try {
			db.execSQL(
				"delete from " + DBContract.ArticleEntry.TABLE_NAME + " where " + DBContract.ArticleEntry.COLUMN_ID + " = " + item.publishedAt.hashCode())
		} catch (e: SQLException) {
			db.close(); return false
		}

		return true
	}

	protected fun changeItem(item: Article): Boolean {
		return false
	}

	protected fun readAll(): ArrayList<Article> {
		val articles by lazy { ArrayList<Article>() }
		val db = database.readableDatabase
		val cursor = db.rawQuery("select * from " + DBContract.ArticleEntry.TABLE_NAME, null)

		cursor?.let {
			cursor.moveToFirst()
			while (!cursor.isAfterLast) {
				articles.add(createObjFromRow(cursor))
				cursor.moveToNext()
			}
		} ?: db.execSQL(DBHelper.SQL_CREATE_ENTRIES) ?: return ArrayList()
		cursor.close()
		return articles
	}

	protected fun getArticlesInRange(from: Int, to: Int): ArrayList<Article> {
		val articles by lazy { ArrayList<Article>() }
		val db = database.readableDatabase
		val cursor = db.rawQuery(
			"select * from " + DBContract.ArticleEntry.TABLE_NAME + " order by " + DBContract.ArticleEntry.COLUMN_ID + " ASC",
			null
		)

		cursor?.let {
			cursor.moveToPosition(from)
			while (cursor.position < to) {
				if (cursor.isAfterLast) return articles
				articles.add(createObjFromRow(cursor))
				cursor.moveToNext()
			}
		} ?: db.execSQL(SQL_CREATE_ENTRIES) ?: return ArrayList()
		cursor.close()
		return articles

	}

	protected fun checkIfRowAlreadyExists(item: Article): Boolean {
		val db = database.readableDatabase

		val cursor = db.rawQuery(
			"select * from " + DBContract.ArticleEntry.TABLE_NAME + " where " + DBContract.ArticleEntry.COLUMN_ID + " = " + item.publishedAt.hashCode()
			, null
		)
		if (cursor.count > 0) {
			cursor.close()
			return true
		}
		cursor.close()
		return false
	}

	private fun getRowValues(article: Article?): ContentValues {
		val content = article?.content ?: ARTICLE_NO_CONTENT
		val author = article?.author ?: ARTICLE_NO_AUTHOR
		val desc = article?.description ?: "No description"

		val values = ContentValues()
		values.put(DBContract.ArticleEntry.COLUMN_ID, article?.publishedAt.hashCode())
		values.put(DBContract.ArticleEntry.COLUMN_SOURCE, article?.source?.getValue(Constants.MAP_SOURCE_KEY_NAME))
		values.put(DBContract.ArticleEntry.COLUMN_AUTHOR, author)
		values.put(DBContract.ArticleEntry.COLUMN_TITLE, article?.title)
		values.put(DBContract.ArticleEntry.COLUMN_DESCRIPTION, desc)
		values.put(DBContract.ArticleEntry.COLUMN_URL, article?.url)
		values.put(DBContract.ArticleEntry.COLUMN_URL_TO_IMAGE, article?.urlToImage)
		values.put(DBContract.ArticleEntry.COLUMN_PUBLISHED_AT, article?.publishedAt)
		values.put(DBContract.ArticleEntry.COLUMN_CONTENT, content)

		return values
	}

	private fun createObjFromRow(cursor: Cursor): Article {
		return Article(
			hashMapOf(Constants.MAP_SOURCE_KEY_NAME to cursor.getString(
				cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_SOURCE))),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_AUTHOR)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_TITLE)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_DESCRIPTION)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_URL)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_URL_TO_IMAGE)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_PUBLISHED_AT)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_CONTENT))
		)
	}

	override fun getAllItems(): Execute<List<Article>> {
		return Execute(::readAll)
	}

	override fun insertItem(item: Article): Execute<Boolean> {
		return Execute(::addItem, item)
	}

	override fun removeItem(item: Article): Execute<Boolean> {
		return Execute(::deleteItem, item)
	}

	override fun updateItem(item: Article): Execute<Boolean> {
		return Execute(::changeItem)
	}

	override fun getArticlesFromTo(from: Int, to: Int): Execute<ArrayList<Article>> {
		return Execute(::getArticlesInRange, from, to)
	}

	override fun checkIfArticleExists(article: Article): Execute<Boolean> {
		return Execute(::checkIfRowAlreadyExists, article)
	}

}