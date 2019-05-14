package com.example.topnews

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.topnews.data.db.AppDatabase
import com.example.topnews.data.networking.ArticleApi
import com.example.topnews.data.repository.ArticleRemoteStorageImpl
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.utils.Constants.API_BASE_URL
import com.facebook.stetho.Stetho
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
	companion object {
		private lateinit var repository: ArticleRepository
		@SuppressLint("StaticFieldLeak")
		private lateinit var contextApp: Context

		fun injectRepository() = repository
		fun getContext() = contextApp
	}

	override fun onCreate() {
		super.onCreate()
		handleTheme()

		contextApp = applicationContext

		repository = setupRepository(AppDatabase.getInstance(applicationContext))
		setupStetho()
	}

	private fun handleTheme() {

		AppCompatDelegate.setDefaultNightMode(
			AppCompatDelegate.MODE_NIGHT_YES
		)
	}

	private fun setupRepository(appDB: AppDatabase) =

		ArticleRepository(
			appDB.tagsDao(),
			appDB.tagArticleDao(),
			appDB.articlesDao(),
			ArticleRemoteStorageImpl(setupRetrofit())
		)

	private fun setupRetrofit(): ArticleApi =

		Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.baseUrl(API_BASE_URL)
			.build()
			.create(ArticleApi::class.java)

	private fun setupStetho() {

		if (BuildConfig.DEBUG) {
			Stetho.initializeWithDefaults(this)
		}
	}

}