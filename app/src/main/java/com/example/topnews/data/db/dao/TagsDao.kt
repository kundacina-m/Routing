package com.example.topnews.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.topnews.data.db.Tag
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TagsDao {
	@Insert(onConflict = OnConflictStrategy.ABORT)
	fun addTag(tag: Tag): Long

	@Insert(onConflict = OnConflictStrategy.ABORT)
	fun addTags(tags: List<Tag>)

	@Delete
	fun removeTag(tag: Tag): Int

	@Query("SELECT * FROM tags")
	fun getAllTags(): Flowable<List<Tag>>

	@Query("SELECT * FROM tags where name == (:name)")
	fun getTagByName(name: String): Single<Tag>

}