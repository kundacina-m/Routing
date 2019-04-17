package com.example.topnews.data.db

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.sqlite.DBContract
import com.example.topnews.sqlite.DBHelper
import com.example.topnews.sqlite.DBHelper.Companion.SQL_CREATE_ENTRIES
import com.example.topnews.utils.Constants
import com.example.topnews.utils.Constants.ARTICLE_NO_AUTHOR
import com.example.topnews.utils.Constants.ARTICLE_NO_CONTENT
import io.reactivex.Flowable

class ArticleDaoImpl : ArticleDao {

	private var database: DBHelper = App.injectDB()

	override fun addItem(item: Article): Flowable<WrappedResponse<Boolean>> {
		return checkIfArticleExists(item)
			.doOnNext {
				if (!it) {
					val db = database.writableDatabase
					db.insert(DBContract.ArticleEntry.TABLE_NAME, null, getRowValues(item))

				}
			}.map {
				OnSuccess(it.not())
			}

		//		if (checkIfRowAlreadyExists(item)) return

	}

	override fun deleteItem(item: Article): Flowable<WrappedResponse<Boolean>> {
		val db = database.writableDatabase

		try {
			db.execSQL(
				"delete from " + DBContract.ArticleEntry.TABLE_NAME + " where " + DBContract.ArticleEntry.COLUMN_ID + " = " + item.publishedAt.hashCode()
			)
		} catch (e: SQLException) {
			db.close(); return Flowable.just(OnSuccess(false))
		}

		return Flowable.just(OnSuccess(true))
	}

	override fun changeItem(item: Article): Flowable<WrappedResponse<Boolean>> {
		return Flowable.just(OnSuccess(false))
	}

	override fun getAllItems(): Flowable<WrappedResponse<ArrayList<Article>>> {
		val articles by lazy { ArrayList<Article>() }
		val db = database.readableDatabase
		val cursor = db.rawQuery("select * from " + DBContract.ArticleEntry.TABLE_NAME, null)

		cursor?.let {
			cursor.moveToFirst()
			while (!cursor.isAfterLast) {
				articles.add(createObjFromRow(cursor))
				cursor.moveToNext()
			}
		} ?: db.execSQL(SQL_CREATE_ENTRIES) ?: return Flowable.just(OnSuccess(ArrayList()))
		cursor.close()
		return Flowable.just(OnSuccess(articles))
	}

	override fun getArticlesFromTo(from: Int, to: Int): Flowable<WrappedResponse<ArrayList<Article>>> {
		val articles by lazy { ArrayList<Article>() }
		val db = database.readableDatabase
		val cursor = db.rawQuery(
			"select * from " + DBContract.ArticleEntry.TABLE_NAME + " order by " + DBContract.ArticleEntry.COLUMN_ID + " ASC",
			null
		)

		cursor?.let {
			cursor.moveToPosition(from)
			while (cursor.position < to) {
				if (cursor.isAfterLast) return Flowable.just(OnSuccess(articles))
				articles.add(createObjFromRow(cursor))
				cursor.moveToNext()
			}
		} ?: db.execSQL(SQL_CREATE_ENTRIES) ?: return Flowable.just(OnSuccess(ArrayList()))
		cursor.close()
		return Flowable.just(OnSuccess(articles))

	}

	override fun checkIfArticleExists(article: Article): Flowable<Boolean> {
		val db = database.readableDatabase

		val cursor = db.rawQuery(
			"select * from " + DBContract.ArticleEntry.TABLE_NAME + " where " + DBContract.ArticleEntry.COLUMN_ID + "" +
				" = " + article.publishedAt.hashCode()
			, null
		)
		if (cursor.count > 0) {
			cursor.close()
			return Flowable.just(true)
		}
		cursor.close()
		return Flowable.just(false)
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
			hashMapOf(
				Constants.MAP_SOURCE_KEY_NAME to cursor.getString(
					cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_SOURCE)
				)
			),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_AUTHOR)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_TITLE)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_DESCRIPTION)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_URL)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_URL_TO_IMAGE)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_PUBLISHED_AT)),
			cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_CONTENT))
		)
	}
}