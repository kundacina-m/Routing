package com.example.topnews.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.topnews.data.model.Article
import com.example.topnews.data.model.SourceRaw
import com.example.topnews.data.model.Tag

@Database(entities = [Article::class, SourceRaw::class, Tag::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

	abstract fun articlesDao(): ArticleDao
	abstract fun tagsDao(): TagsDao
}