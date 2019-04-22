package com.example.topnews

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import com.example.topnews.data.db.ArticleDao
import com.example.topnews.data.db.ArticleDatabase
import com.example.topnews.data.networking.ArticleApi
import com.example.topnews.data.repository.ArticleRemoteStorageImpl
import com.example.topnews.data.repository.ArticleRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
	companion object {
		private lateinit var articleApi: ArticleApi
		private lateinit var repository: ArticleRepository
		private lateinit var remoteStorage: ArticleRemoteStorageImpl
		private lateinit var articleDatabase: ArticleDatabase
		private lateinit var articleDao: ArticleDao

		fun injectApi() = articleApi
		fun injectRepository() = repository

		fun injectRemoteStorage() = remoteStorage

		fun injectArticleDao() = articleDao

	}

	private lateinit var retrofit: Retrofit

	override fun onCreate() {
		super.onCreate()

		AppCompatDelegate.setDefaultNightMode(
			AppCompatDelegate.MODE_NIGHT_YES
		)

		val appContext = applicationContext

		retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.baseUrl("https://newsapi.org/v2/")
			.build()

		articleApi = retrofit.create(ArticleApi::class.java)


		articleDatabase = Room.databaseBuilder(applicationContext, ArticleDatabase::class.java, "article-db").build()
		articleDao = articleDatabase.articlesDao()


		repository = ArticleRepository()

		remoteStorage = ArticleRemoteStorageImpl()

	}
}