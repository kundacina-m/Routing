package com.example.topnews.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.topnews.data.db.Article
import io.reactivex.Single

@Dao
interface ArticleDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addItem(article: Article): Long

	@Delete
	fun deleteItem(article: Article): Int

	@Query("SELECT * FROM articles where url = :url")
	fun getItem(url: String): Single<Article>

	@Query("SELECT * FROM articles where url in (:listId)")
	fun getItemsById(listId: List<String>): Single<List<Article>>

	@Query("SELECT * FROM articles order by url ASC")
	fun getArticlesPagination(): DataSource.Factory<Int, Article>

	@Query("SELECT * FROM articles")
	fun getAll() : Single<List<Article>>


}