package com.example.topnews.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.topnews.data.model.Article
import com.example.topnews.data.model.TagArticle
import com.example.topnews.data.model.TagWithArticles
import io.reactivex.Single

@Dao
interface TagArticleDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun addTagArticleRow(tagArticle: TagArticle)

	@Query("SELECT * from   TagArticle ")
	fun getAll(): Single<List<TagArticle>>

	@Query("SELECT articleId from TagArticle where tagName == :tagName")
	fun getArticleIdsByTag(tagName: String): Single<List<String>>

	@Query(
		""" SELECT * from
		articles a left join TagArticle t
		on a.publishedAt = t.articleId
		where t.tagName == :tagName"""
	)
	fun getArticlesByTag(tagName: String): Single<List<Article>>

	@Query(
		"""SELECT * from tags ta
			left join TagArticle t on ta.name = t.tagName
			left join articles a on a.publishedAt = t.articleId"""
	)
	fun getTagsWithArticles(): Single<List<TagWithArticles>>

}