package com.example.topnews.data.repository

import androidx.paging.DataSource
import com.example.topnews.data.db.Article
import com.example.topnews.data.db.Tag
import com.example.topnews.data.db.TagArticle
import com.example.topnews.data.db.dao.ArticleDao
import com.example.topnews.data.db.dao.TagArticleDao
import com.example.topnews.data.db.dao.TagsDao
import com.example.topnews.domain.ArticleRemoteStorage
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.utils.Constants.API_CATEGORY
import com.example.topnews.utils.Constants.API_PAGE
import com.example.topnews.utils.Constants.API_PAGE_SIZE
import com.example.topnews.utils.Constants.API_QUERY
import com.example.topnews.utils.toSealed
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleRepository @Inject constructor(
	private val tagsDao: TagsDao,
	private val tagArticleDao: TagArticleDao,
	private val articleDao: ArticleDao,
	private val remoteStorage: ArticleRemoteStorage
) {

	// region local calls

	fun addToDB(item: Article): Single<WrappedResponse<Long>> =
		Single.just(articleDao.addItem(item)).toSealed()

	fun removeFromDB(item: Article): Single<WrappedResponse<Int>> =
		Single.just(articleDao.deleteItem(item)).toSealed()

	fun getArticlesPagination(): DataSource.Factory<Int, Article> =
		articleDao.getArticlesPagination()

	fun getAll(): Single<List<Article>> =
		articleDao.getAll()

	fun checkIfArticleExistsInDB(article: Article): Single<WrappedResponse<Article>> =
		articleDao.getItem(article.url)
			.toSealed()

	fun addTag(articleId: String, tag: String) =
		Completable.create {
			tagsDao.addTag(Tag(tag))
			tagArticleDao.addTagArticleRow(TagArticle(tag, articleId))
			it.onComplete()
		}
			.subscribeOn(Schedulers.io())
			.subscribe()

	fun removeTagArticle(articleId: String) =
		Completable.create {
			tagArticleDao.removeTagArticle(articleId)
			it.onComplete()
		}
			.subscribeOn(Schedulers.io())
			.subscribe()

	fun getArticlesFromTag(tagName: String): Single<List<Article>> =
		tagArticleDao.getArticlesByTag(tagName)

	// endregion local calls

	// region network calls

	fun getAllRemote(pageNum: Int, pageSize: Int): Single<WrappedResponse<List<Article>>> =
		remoteStorage.getItemsByQuery(
			mapOf(
				API_PAGE to pageNum.toString(),
				API_PAGE_SIZE to pageSize.toString()
			)
		)

	fun getByQueryRemote(query: String, pages: Int, pageSize: Int): Single<WrappedResponse<List<Article>>> =
		remoteStorage.getItemsByQuery(
			mapOf(
				API_QUERY to query,
				API_PAGE to pages.toString(),
				API_PAGE_SIZE to pageSize.toString()
			)
		)

	fun getArticlesByCategory(category: String, pageNum: Int, pageSize: Int): Single<WrappedResponse<List<Article>>> =
		remoteStorage.getItemsByQuery(
			mapOf(
				API_PAGE to pageNum.toString(),
				API_PAGE_SIZE to pageSize.toString(),
				API_CATEGORY to category
			)
		)

	// endregion network calls

}