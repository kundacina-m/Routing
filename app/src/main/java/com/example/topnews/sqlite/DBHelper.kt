package com.example.topnews.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
	override fun onCreate(db: SQLiteDatabase?) {
		db?.execSQL(SQL_CREATE_ENTRIES)
	}

	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		db?.execSQL(SQL_DELETE_ENTRIES)
		onCreate(db)
	}

	companion object {
		var DATABASE_VERSION = 1
		const val DATABASE_NAME = "SQLite.com.example.topnews.db"

		const val SQL_CREATE_ENTRIES =
			"CREATE TABLE " + DBContract.ArticleEntry.TABLE_NAME + " (" +
				DBContract.ArticleEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
				DBContract.ArticleEntry.COLUMN_SOURCE + " TEXT," +
				DBContract.ArticleEntry.COLUMN_AUTHOR + " TEXT," +
				DBContract.ArticleEntry.COLUMN_TITLE + " TEXT," +
				DBContract.ArticleEntry.COLUMN_DESCRIPTION + " TEXT," +
				DBContract.ArticleEntry.COLUMN_URL + " TEXT," +
				DBContract.ArticleEntry.COLUMN_URL_TO_IMAGE + " TEXT," +
				DBContract.ArticleEntry.COLUMN_PUBLISHED_AT + " TEXT," +
				DBContract.ArticleEntry.COLUMN_CONTENT + " TEXT)"

		private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.ArticleEntry.TABLE_NAME
	}
}