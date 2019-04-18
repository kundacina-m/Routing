package com.example.topnews

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.topnews.data.networking.ArticleApi
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.data.repository.ArticleLocalStorageImpl
import com.example.topnews.data.repository.ArticleRemoteStorageImpl
import com.example.topnews.data.db.sqlite.DBHelper
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
	companion object {
		private lateinit var articleApi: ArticleApi
		private lateinit var database: DBHelper
		private lateinit var repository: ArticleRepository
		private lateinit var localStorage: ArticleLocalStorageImpl
		private lateinit var remoteStorage: ArticleRemoteStorageImpl

		fun injectDB() = database
		fun injectApi() = articleApi
		fun injectRepository() = repository

		fun injectLocalStorage() = localStorage
		fun injectRemoteStorage() = remoteStorage
	}

	private lateinit var retrofit: Retrofit

	override fun onCreate() {
		super.onCreate()

		AppCompatDelegate.setDefaultNightMode(
			AppCompatDelegate.MODE_NIGHT_YES)

		val appContext = applicationContext

		retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.baseUrl("https://newsapi.org/v2/")
			.build()

		articleApi = retrofit.create(ArticleApi::class.java)

		database = DBHelper(appContext)

		repository = ArticleRepository()

		localStorage = ArticleLocalStorageImpl()
		remoteStorage = ArticleRemoteStorageImpl()

	}
}