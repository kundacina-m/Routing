package com.example.topnews.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.topnews.data.model.SourceRaw

@Dao
interface SourceDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun addSource(source: SourceRaw): Long

	@Delete
	fun removeSource(source: SourceRaw) : Int

	@Query("SELECT * FROM sources")
	fun getAllSources() : List<SourceRaw>

	@Query("SELECT * FROM sources where name == (:name)")
	fun getSourceByName(name: String) : SourceRaw
}