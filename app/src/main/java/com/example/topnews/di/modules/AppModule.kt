package com.example.topnews.di.modules

import android.content.Context
import com.example.topnews.NewsApplication
import com.example.topnews.data.db.AppDatabase
import com.example.topnews.data.db.dao.ArticleDao
import com.example.topnews.data.db.dao.TagArticleDao
import com.example.topnews.data.db.dao.TagsDao
import com.example.topnews.data.networking.ArticleApi
import com.example.topnews.data.repository.ArticleRemoteStorageImpl
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.ArticleRemoteStorage
import com.example.topnews.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

	@Provides
	fun provideContext(application: NewsApplication): Context {
		return application.applicationContext
	}

	@Singleton
	@Provides
	fun provideArticleApi(): ArticleApi =
		Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.baseUrl(Constants.API_BASE_URL)
			.build()
			.create(ArticleApi::class.java)

	@Singleton
	@Provides
	fun provideDb(appContext: Context): AppDatabase =
		AppDatabase.getInstance(appContext)

	@Singleton
	@Provides
	fun provideArticlesDao(db: AppDatabase) =
		db.articlesDao()

	@Singleton
	@Provides
	fun provideTagsDao(db: AppDatabase) =
		db.tagsDao()

	@Singleton
	@Provides
	fun provideTagArticlesDao(db: AppDatabase) =
		db.tagArticleDao()

	@Singleton
	@Provides
	fun providesRemoteStorage(api: ArticleApi): ArticleRemoteStorage =
		ArticleRemoteStorageImpl(api)

	@Singleton
	@Provides
	fun provideRepository(
		tagsDao: TagsDao, articlesDao: ArticleDao,
		tagArticleDao: TagArticleDao, remoteStorage: ArticleRemoteStorage
	) =
		ArticleRepository(tagsDao, tagArticleDao, articlesDao, remoteStorage)

}