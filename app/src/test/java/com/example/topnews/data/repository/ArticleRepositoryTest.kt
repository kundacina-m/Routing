package com.example.topnews.data.repository

import com.example.topnews.data.db.Article
import com.example.topnews.data.db.dao.ArticleDao
import com.example.topnews.data.db.dao.TagArticleDao
import com.example.topnews.data.db.dao.TagsDao
import com.example.topnews.domain.ArticleRemoteStorage
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

// region constants

const val TAG_NAME = "tag name"
const val PAGE_ONE = 1
const val PAGE_SIZE = 5
const val CATEGORY = "category"
const val QUERY = "query"

// endregion constants

@ExtendWith(MockKExtension::class)
class ArticleRepositoryTest {

	// region helper fields

	private var params = mapOf("page" to PAGE_ONE.toString(), "pageSize" to PAGE_SIZE.toString())
	private var paramsWithQuery = mapOf("q" to QUERY, "page" to PAGE_ONE.toString(), "pageSize" to PAGE_SIZE.toString())
	private var paramsForCategory =
		mapOf(CATEGORY to "general", "page" to PAGE_ONE.toString(), "pageSize" to PAGE_SIZE.toString())

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
		SUT = ArticleRepository(tagDao, tagArticleDao, localStorage, remoteStorage)
	}

	// region tags tests
	@Test
	fun getArticlesFromTag_successfully_assertTwoItems() {

		every { tagArticleDao.getArticlesByTag(TAG_NAME) } returns Single.just(listOf(first, second))

		SUT.getArticlesFromTag(TAG_NAME)
			.test()
			.assertValues(listOf(first, second))
			.dispose()

	}

	@Test
	fun getArticlesFromTag_successfully_assertZeroItems() {

		every { tagArticleDao.getArticlesByTag(TAG_NAME) } returns Single.just(emptyList())

		SUT.getArticlesFromTag(TAG_NAME)
			.test()
			.assertValues(emptyList())
			.dispose()
	}

	// endregion tags tests

	// region remote tests

	@Test
	fun getAllRemote_assertEmpty() {

		every { remoteStorage.getItemsByQuery(params) } returns Single.just(OnSuccess(emptyList()))

		SUT.getAllRemote(PAGE_ONE, PAGE_SIZE)
			.test()
			.assertValues(OnSuccess(emptyList()))
			.dispose()
	}

	@Test
	fun getAllRemote_assertNotEmpty() {

		every { remoteStorage.getItemsByQuery(params) } returns Single.just(OnSuccess(listOf(first, second)))

		SUT.getAllRemote(PAGE_ONE, PAGE_SIZE)
			.test()
			.assertValues(OnSuccess(listOf(first, second)))
			.dispose()
	}

	@Test
	fun getArticlesByQuery_assertEmpty() {
		every { remoteStorage.getItemsByQuery(paramsWithQuery) } returns Single.just(OnSuccess(emptyList()))

		SUT.getByQueryRemote(QUERY, PAGE_ONE, PAGE_SIZE)
			.test()
			.assertValues(OnSuccess(emptyList()))
			.dispose()
	}

	@Test
	fun getArticlesByQuery_assertNotEmpty() {
		every { remoteStorage.getItemsByQuery(paramsWithQuery) } returns Single.just(OnSuccess(listOf(first, second)))

		SUT.getByQueryRemote(QUERY, PAGE_ONE, PAGE_SIZE)
			.test()
			.assertValues(OnSuccess(listOf(first, second)))
			.dispose()
	}

	@Test
	fun getArticlesByCategory_assertEmpty() {
		every { remoteStorage.getItemsByQuery(paramsForCategory) } returns Single.just(OnSuccess(emptyList()))

		SUT.getArticlesByCategory(paramsForCategory[CATEGORY]!!, PAGE_ONE, PAGE_SIZE)
			.test()
			.assertValues(OnSuccess(emptyList()))
			.dispose()
	}

	@Test
	fun getArticlesByCategory_assertNotEmpty() {
		every { remoteStorage.getItemsByQuery(paramsForCategory) } returns Single.just(OnSuccess(listOf(first, second)))

		SUT.getArticlesByCategory(paramsForCategory[CATEGORY]!!, PAGE_ONE, PAGE_SIZE)
			.test()
			.assertValues(OnSuccess(listOf(first, second)))
			.dispose()
	}

	// endregion remote tests

	// region local tests
	@Test
	fun getAllLocal_assertEmptyList() {

		// TODO

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
		every { localStorage.getItem(first.url) } returns Single.just(first)

		SUT.checkIfArticleExistsInDB(first)
			.test()
			.assertValue(OnSuccess(first))
			.dispose()

	}

	@Test
	fun insertItemLocal_assertTrue() {
		every { localStorage.addItem(first) } returns 1

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