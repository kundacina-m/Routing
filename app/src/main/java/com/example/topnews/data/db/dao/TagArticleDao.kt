package com.example.topnews.data.db.dao

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.topnews.data.db.Article
import com.example.topnews.data.db.TagArticle
import io.reactivex.Single

@Dao
interface TagArticleDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun addTagArticleRow(tagArticle: TagArticle)

	@VisibleForTesting
	@Query("SELECT * from   TagArticle ")
	fun getAll(): Single<List<TagArticle>>

	@Query("SELECT articleId from TagArticle where tagName == :tagName")
	fun getArticleIdsByTag(tagName: String): Single<List<String>>

	@Query(
		""" SELECT * from
		articles a left join TagArticle t
		on a.url = t.articleId
		where t.tagName == :tagName"""
	)
	fun getArticlesByTag(tagName: String): Single<List<Article>>

}