package com.example.topnews.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ArticleDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addItem(article: Article): Long

	@Query("SELECT * FROM articles")
	fun getAllItems(): Flowable<List<Article>>

	@Delete
	fun deleteItem(article: Article): Int

	@Query("SELECT * FROM articles where publishedAt = :publishedAt")
	fun getItem(publishedAt: String): Single<Article>

	@Query("SELECT * FROM articles where publishedAt in (:listId)")
	fun getItemsById(listId: List<String>): Single<List<Article>>

}