//package com.example.topnews.data.repository
//
//import com.example.topnews.data.db.Article
//import com.example.topnews.data.model.ArticleRaw
//import com.example.topnews.data.db.Source
//import com.example.topnews.data.networking.ArticleApi
//import com.example.topnews.data.networking.responses.ResponseArticle
//import com.example.topnews.domain.WrappedResponse.OnSuccess
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.spyk
//import io.reactivex.Single
//import junit.framework.TestCase
//import org.junit.Before
//import org.junit.ClassRule
//import org.junit.Test
//
//class ArticleRemoteStorageImplTest {
//
//	// region constants
//
//	// endregion constants
//
//	// region helper fields
//
//	private val articleApi = spyk<ArticleApi>()
//
//	// endregion helper fields
//	private lateinit var SUT: ArticleRemoteStorageImpl
//
//
//	@Before
//	fun setup() {
//		SUT = ArticleRemoteStorageImpl(articleApi)
//	}
//
//	@Test
//	fun getAll_assertEmpty() {
//
//		every { articleApi.getArticles() } returns Single.just(
//			ResponseArticle(
//				status = "ok", totalResults = 2,
//				articles = emptyList()
//			)
//		)
//
//		SUT.getAll()
//			.test()
//			.assertValues(OnSuccess(emptyList()))
//			.dispose()
//
//	}
//
//	@Test
//	fun getAll_assertNotEmpty() {
//		every { articleApi.getArticles() } returns Single.just(
//			ResponseArticle(
//				status = "ok", totalResults = 2,
//				articles = listOf(createArticleRaw(1),createArticleRaw(2))
//			)
//		)
//
//		SUT.getAll()
//			.test()
//			.assertValues(OnSuccess(listOf(createArticle(1),createArticle(2))))
//			.dispose()
//
//	}
//
//	// region helper methods
//
//	// endregion helper methods
//
//	// region helper classes
//
//	private fun createArticle(num: Int): Article {
//		return Article(
//			num.toString(),
//			num.toString(),
//			num.toString(),
//			num.toString(),
//			num.toString(),
//			num.toString(),
//			num.toString(),
//			num.toString()
//		)
//	}
//
//	private fun createArticleRaw(num: Int): ArticleRaw {
//		return ArticleRaw(
//			Source(num.toString(), num.toString()),
//			num.toString(),
//			num.toString(),
//			num.toString(),
//			num.toString(),
//			num.toString(),
//			num.toString(),
//			num.toString()
//		)
//	}
//
//	// endregion helper classes
//}