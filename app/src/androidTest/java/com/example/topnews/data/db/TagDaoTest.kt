package com.example.topnews.data.db

import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.topnews.data.db.dao.TagsDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TagDaoTest {

	private val first = Tag("first")
	private val second = Tag("second")

	private lateinit var tagDao: TagsDao
	private lateinit var db: AppDatabase

	@Before
	fun setup() {

		db =
			Room.inMemoryDatabaseBuilder(
				InstrumentationRegistry.getInstrumentation().targetContext,
				AppDatabase::class.java
			).build()

		tagDao = db.tagsDao()
	}

	@After
	@Throws(IOException::class)
	fun closeDB() {

		db.close()
	}

	@Test
	fun addTag_assertForID() {
		tagDao.addTag(first)

		tagDao.getTagByName("first")
			.test()
			.assertValue(first)
			.dispose()
	}

	@Test
	fun removeTag_successfully_assertOneDeletedRow() {
		tagDao.addTag(first)

		val removed = tagDao.removeTag(first)
		Assert.assertEquals(removed, 1)
	}

	@Test
	fun removeTag_failed_assertZeroDeletedRows() {
		val removed = tagDao.removeTag(first)
		Assert.assertEquals(removed, 0)
	}

	@Test
	fun getAllTags_emptyDB_assertEmptyList() {
		tagDao.getAllTags()
			.test()
			.assertEmpty()
			.dispose()
	}

	@Test
	fun getAllTags_twoTagsInDB_assertTwoTags() {
		tagDao.addTag(first)
		tagDao.addTag(second)

		tagDao.getAllTags()
			.test()
			.assertValues(listOf(first, second))
			.dispose()

	}

	@Test
	fun getTagByName_successfully_assertForTag() {
		tagDao.addTag(first)

		tagDao.getTagByName("first")
			.test()
			.assertValue(first)
			.dispose()

	}

	@Test
	fun getTagByName_failed_assertForException() {
		tagDao.getTagByName("first")
			.test()
			.assertError(EmptyResultSetException::class.java)
			.dispose()
	}

	// region helper functions

	// endregion helper functions

}