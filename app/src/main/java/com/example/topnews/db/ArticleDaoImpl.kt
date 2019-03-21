package com.example.topnews.db

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.topnews.utils.App
import com.example.topnews.screens.Article
import com.example.topnews.utils.Constants
import com.example.topnews.utils.Constants.ARTICLE_NO_AUTHOR
import com.example.topnews.utils.Constants.ARTICLE_NO_CONTENT
import com.example.topnews.sqlite.DBHelper
import com.example.topnews.sqlite.DBHelper.Companion.SQL_CREATE_ENTRIES
import com.example.topnews.sqlite.DBContract

class ArticleDaoImpl : ArticleDao {

    private var database: DBHelper = App.injectDB()

    override fun insertItem(item: Article): Boolean {
        var db = database.readableDatabase
        if (checkIfRowAlreadyExists(db, item)) return false

        db = database.writableDatabase
        db.insert(DBContract.ArticleEntry.TABLE_NAME, null, getRowValues(item))

        return true
    }

    override fun removeItem(item: Article): Boolean {
        val db = database.writableDatabase

        val queryToDelete =
            "delete from " + DBContract.ArticleEntry.TABLE_NAME + " where " + DBContract.ArticleEntry.COLUMN_ID + " = " + item.hashCode()

        try {
            db.execSQL(queryToDelete)
        } catch (e: SQLException) {
            db.close()
            return false
        }

        db.close()
        return true
    }

    override fun updateItem(item: Article): Boolean {
        return false
    }

    override fun readAll(): ArrayList<Article> {
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

    override fun getArticlesFromTo(from: Int, to: Int): ArrayList<Article> {
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

    private fun checkIfRowAlreadyExists(db: SQLiteDatabase, item: Article): Boolean {
        val queryToFind =
            "select * from " + DBContract.ArticleEntry.TABLE_NAME + " where " + DBContract.ArticleEntry.COLUMN_ID + " = " + item.hashCode()

        val cursor = db.rawQuery(queryToFind, null)
        if (cursor.count > 0) {
            removeItem(item)
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }

    private fun getRowValues(article: Article?): ContentValues {
        val cont = article?.content ?: ARTICLE_NO_CONTENT
        val auth = article?.author ?: ARTICLE_NO_AUTHOR

        val values = ContentValues()
        values.put(DBContract.ArticleEntry.COLUMN_ID, article.hashCode())
        values.put(DBContract.ArticleEntry.COLUMN_SOURCE, article?.source?.getValue(Constants.MAP_SOURCE_KEY_NAME))
        values.put(DBContract.ArticleEntry.COLUMN_AUTHOR, auth)
        values.put(DBContract.ArticleEntry.COLUMN_TITLE, article?.title)
        values.put(DBContract.ArticleEntry.COLUMN_DESCRIPTION, article?.description)
        values.put(DBContract.ArticleEntry.COLUMN_URL, article?.url)
        values.put(DBContract.ArticleEntry.COLUMN_URL_TO_IMAGE, article?.urlToImage)
        values.put(DBContract.ArticleEntry.COLUMN_PUBLISHED_AT, article?.publishedAt)
        values.put(DBContract.ArticleEntry.COLUMN_CONTENT, cont)

        return values
    }

    private fun createObjFromRow(cursor: Cursor): Article {
        return Article(
            hashMapOf(Constants.MAP_SOURCE_KEY_NAME to cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_SOURCE))),
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