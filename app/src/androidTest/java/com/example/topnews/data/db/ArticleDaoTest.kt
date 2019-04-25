package com.example.topnews.data.db

import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.topnews.data.model.Article
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

// region constants
const val ONE = "1"
const val TWO = "2"
// endregion constants

class ArticleDaoTest {

	// region helper fields

	private val first = createArticle(1)
	private val second = createArticle(2)

	// endregion helper fields

	private lateinit var articleDao: ArticleDao
	private lateinit var db: AppDatabase

	@Before
	fun setup() {

		db =
			Room.inMemoryDatabaseBuilder(
				InstrumentationRegistry.getInstrumentation().targetContext,
				AppDatabase::class.java
			).build()

		articleDao = db.articlesDao()
	}

	@After
	@Throws(IOException::class)
	fun closeDB() {

		db.close()
	}

	// region Article Dao

	@Test
	fun insertItem_successfully_assertForID() {

		articleDao.addItem(first)
		articleDao.getItem(ONE)
			.test()
			.assertValue(first)
	}

	@Test
	fun insertItem_insert2Items_AssertFor2Items() {

		articleDao.addItem(first)
		articleDao.addItem(second)

		articleDao.getItem(TWO)
			.test()
			.assertValue(second)

		articleDao.getItem(ONE)
			.test()
			.assertValue(first)
	}

	@Test
	fun getAll_emptyDB_returnEmpty() {

		articleDao.getAllItems()
			.test()
			.assertEmpty()

	}

	@Test
	fun getAll_insert2Items_Assert2Items() {

		articleDao.addItem(first)
		articleDao.addItem(second)

		articleDao.getAllItems()
			.test()
			.assertValues(listOf(first, second))
			.assertNoErrors()
			.dispose()
	}

	@Test
	fun getItem_notExisting_assertForException() {

		articleDao.getItem(ONE)
			.test()
			.assertError(EmptyResultSetException::class.java)
			.dispose()
	}

	@Test
	fun deleteItem_notExisting_assertForZeroDeletedRows() {

		val deleted = articleDao.deleteItem(first)
		Assert.assertEquals(0, deleted)
	}

	@Test
	fun deleteItem_existingItem_AssertForOneDeletedRow() {

		articleDao.addItem(first)
		val deleted = articleDao.deleteItem(first)
		Assert.assertEquals(1, deleted)
	}

	@Test
	fun getArticleByIds_successfully_return2Items() {
		articleDao.addItem(first)
		articleDao.addItem(second)

		articleDao.getItemsById(listOf("1","2"))
			.test()
			.assertValues(listOf(first,second))
			.dispose()
	}

	// endregion Article Dao

	// region helper functions

	private fun createArticle(num: Int): Article {
		return Article(
			num.toString(),
			num.toString(),
			num.toString(),
			num.toString(),
			num.toString(),
			num.toString(),
			num.toString(),
			num.toString()
		)
	}

	// endregion helper functions

}