package com.example.topnews.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.topnews.data.model.Tag
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TagsDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun addTag(tag: Tag): Long

	@Delete
	fun removeTag(tag: Tag): Int

	@Query("SELECT * FROM tags")
	fun getAllTags(): Flowable<List<Tag>>

	@Query("SELECT * FROM tags where name == (:name)")
	fun getTagByName(name: String): Single<Tag>
}