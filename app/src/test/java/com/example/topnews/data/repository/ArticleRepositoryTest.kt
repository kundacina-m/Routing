package com.example.topnews.data.repository

import com.example.topnews.data.db.dao.ArticleDao
import com.example.topnews.data.db.dao.TagArticleDao
import com.example.topnews.data.db.dao.TagsDao
import com.example.topnews.data.db.Article
import com.example.topnews.domain.ArticleRemoteStorage
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(MockKExtension::class)
class ArticleRepositoryTest {

	// region constants

	private var params = mapOf<String,String>("page" to "1","pageSize" to "2")

	// endregion constants

	// region helper fields

	private val first = createArticle(1)
	private val second = createArticle(2)

	private val localStorage: ArticleDao = mockk()
	private val remoteStorage: ArticleRemoteStorage = mockk()
	private val tagDao: TagsDao = mockk()
	private val tagArticleDao: TagArticleDao = mockk()

	// endregion helper fields
	private lateinit var SUT: ArticleRepository

	@Before
	fun setup() {
		SUT = ArticleRepository(tagDao,tagArticleDao,localStorage, remoteStorage)
	}

	// region tags tests
	@Test
	fun getArticlesFromTag_successfully_assertTwoItems() {

		every { tagArticleDao.getArticlesByTag("1") } returns Single.just(listOf(first,second))


		SUT.getArticlesFromTag("1")
			.test()
			.assertValues(listOf(first,second))

	}

	// endregion tags tests

	// region remote tests

	@Test
	fun getAllRemote_assertEmpty() {

		// TODO

		every { remoteStorage.getItemsByQuery(params) } returns Single.just(OnSuccess(emptyList()))

//		every { remoteStorage.getAll() } returns Single.just(OnSuccess(emptyList()))
//
//		SUT.getAllRemote()
//			.test()
//			.assertValues(OnSuccess(emptyList()))
//			.dispose()
	}

	@Test
	fun getAllRemote_assertNotEmpty() {

		// TODO

//		every { remoteStorage.getAll() } returns Single.just(OnSuccess(listOf(first, second)))
//
//		SUT.getAllRemote()
//			.test()
//			.assertValues(OnSuccess(listOf(first, second)))
//			.dispose()
	}

	@Test
	fun getArticlesByPages_assertEmpty() {
		every {
			remoteStorage.getItemsByQuery(
				mapOf(
					Constants.API_QUERY to "query",
					Constants.API_PAGE to "1",
					Constants.API_PAGESIZE to "6"
				)
			)
		} returns Single.just(OnSuccess(emptyList()))

		SUT.getByQueryRemote("query", 1)
			.test()
			.assertValues(OnSuccess(emptyList()))
			.dispose()
	}

	@Test
	fun getArticlesByPages_assertNotEmpty() {
		every {
			remoteStorage.getItemsByQuery(
				mapOf(
					Constants.API_QUERY to "query",
					Constants.API_PAGE to "1",
					Constants.API_PAGESIZE to "6"
				)
			)
		} returns Single.just(OnSuccess(listOf(first, second)))

		SUT.getByQueryRemote("query", 1)
			.test()
			.assertValues(OnSuccess(listOf(first, second)))
			.dispose()
	}

	@Test
	fun getArticlesByCategory_assertEmpty() {
		every {
			remoteStorage.getItemsByQuery(
				mapOf(
					Constants.API_CATEGORY to "pro"
				)
			)
		} returns Single.just(OnSuccess(emptyList()))

		SUT.getArticlesByCategory("pro")
			.test()
			.assertValues(OnSuccess(emptyList()))
			.dispose()
	}

	@Test
	fun getArticlesByCategory_assertNotEmpty() {
		every {
			remoteStorage.getItemsByQuery(
				mapOf(
					Constants.API_CATEGORY to "pro"
				)
			)
		} returns Single.just(OnSuccess(listOf(first, second)))

		SUT.getArticlesByCategory("pro")
			.test()
			.assertValues(OnSuccess(listOf(first, second)))
			.dispose()
	}

	// endregion remote tests

	// region local tests
	@Test
	fun getAllLocal_assertEmptyList() {

		// TODO

//		every { localStorage.getAllItems() } returns Flowable.just(emptyList())
//
//		SUT.getAllFromDB()
//			.test()
//			.assertValues(OnSuccess(emptyList()))
//			.dispose()
	}

	@Test
	fun getAllLocal_assertNotEmptyList() {

		// TODO

//		every { localStorage.getAllItems() } returns Flowable.just(listOf(first, second))
//
//		SUT.getAllFromDB()
//			.test()
//			.assertValues(OnSuccess(listOf(first, second)))
//			.dispose()
	}

	@Test
	fun checkIfInDB_assertArticle() {
		every { localStorage.getItem("1") } returns Single.just(first)

		SUT.checkIfArticleExistsInDB(first)
			.test()
			.assertValue(OnSuccess(first))
			.dispose()

	}

	@Test
	fun insertItemLocal_assertTrue() {
		every { localStorage.addItem(first) } returns 1.toLong()

		SUT.addToDB(first)
			.test()
			.assertValue(OnSuccess(1.toLong()))
			.dispose()
	}

	@Test
	fun remoteItemLocal_assertZeroRemoved() {
		every { localStorage.deleteItem(first) } returns 0

		SUT.removeFromDB(first)
			.test()
			.assertValue(OnSuccess(0))
			.dispose()
	}

	@Test
	fun remoteItemLocal_assertOneRemoved() {
		every { localStorage.deleteItem(first) } returns 1

		SUT.removeFromDB(first)
			.test()
			.assertValue(OnSuccess(1))
			.dispose()
	}

	//	@Test
	//	fun checkIfInDB_assertException() {
	//		every { localStorage.getItem("1") } throws Exception()
	//
	//		SUT.checkIfArticleExistsInDB(first)
	//			.test()
	//			.assertError(EmptyResultSetException::class.java)
	//			.dispose()
	//
	//	}

	// endregion local tests

	// region helper methods

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

	// endregion helper methods

	// region helper classes

	// endregion helper classes
}