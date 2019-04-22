package com.example.topnews.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.topnews.data.model.Article


@Database(entities = [(Article::class)], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {

	abstract fun articlesDao(): ArticleDao
}