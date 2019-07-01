package com.example.topnews.data.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.topnews.data.db.dao.TagArticleDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.Date

class TagArticleDaoTest {

	private lateinit var tagArticleDao: TagArticleDao
	private lateinit var db: AppDatabase

	private val first = TagArticle("1", "1")
	private val second = TagArticle("1", "2")
	private val third = TagArticle("3", "3")

	@Before
	fun setup() {

		db =
			Room.inMemoryDatabaseBuilder(
				InstrumentationRegistry.getInstrumentation().targetContext,
				AppDatabase::class.java
			).build()

		tagArticleDao = db.tagArticleDao()
	}

	@After
	@Throws(IOException::class)
	fun closeDB() {

		db.close()
	}

	@Test
	fun addTagArticle_successfully_assertTableNotEmpty() {
//		tagArticleDao.addTagArticleRow(first)
//		tagArticleDao.getAll()
//			.test()
//			.assertValueCount(1)
//			.dispose()

	}

	@Test
	fun addTagArticle_add2ItemsSuccessfully_assertTwoItems() {

//		tagArticleDao.addTagArticleRow(first)
//		tagArticleDao.addTagArticleRow(second)
//		tagArticleDao.addTagArticleRow(third)
//
//		val list = tagArticleDao.getAll()
//
//		Assert.assertEquals(list.blockingGet().size, 3)
	}

	@Test
	fun getArticlesByTag_successfully_assertItem() {

//		tagArticleDao.addTagArticleRow(first)
//		tagArticleDao.addTagArticleRow(second)
//
//		tagArticleDao.getArticleIdsByTag("1")
//			.test()
//			.assertValue {
//				it[0] =="1"
//			}
//			.dispose()

		//		Assert.assertEquals(listId.blockingGet()[0], "1")
	}

	@Test
	fun getArticlesByTag_successfully2ArticlesId_assertReturn2() {

//		tagArticleDao.addTagArticleRow(first)
//		tagArticleDao.addTagArticleRow(second)
//
//		tagArticleDao.getArticleIdsByTag("1")
//			.map { it.size }
//			.test()
//			.assertValue(2)
//			.dispose()

	}

	@Test
	fun getArticlesByTag_assertSth() {

//		arrangeDB()
//
//		tagArticleDao.getArticlesByTag("tag1")
//			.test()
//			.assertValue {
//				it.size == 2 && it[0] == createArticle(1)
//			}.dispose()

	}

//	@Test
//	fun getTagsWithArticles_successfully() {
//
//		arrangeDB()
//
//		tagArticleDao.getTagsWithArticles()
//			.test()
//			.assertValue {
//				it.size == 4
//			}.dispose()
//
//	}

	private fun arrangeDB() {
		addArticle(1)
		addArticle(2)
		addArticle(3)
		addTags()

		tagArticleDao.addTagArticleRow(TagArticle("tag1", "1"))
		tagArticleDao.addTagArticleRow(TagArticle("tag2", "1"))
		tagArticleDao.addTagArticleRow(TagArticle("tag1", "2"))
		tagArticleDao.addTagArticleRow(TagArticle("tag3", "2"))
	}

	private fun addArticle(int: Int){
		db.articlesDao().addItem(createArticle(int))
	}

	private fun addTags(){
		db.tagsDao().apply {
			addTags(listOf(
				Tag("tag1"),
				Tag("tag2"),
				Tag("tag3"),
				Tag("tag4")
			))
		}
	}

	private fun createArticle(num: Int): Article {
		return Article(
			num.toString(),
			num.toString(),
			num.toString(),
			num.toString(),
			num.toString(),
			num.toString(),
			Date(),
			num.toString()
		)
	}


}