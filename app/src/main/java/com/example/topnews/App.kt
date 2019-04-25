package com.example.topnews

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.topnews.data.db.AppDatabase
import com.example.topnews.data.db.ArticleDao
import com.example.topnews.data.networking.ArticleApi
import com.example.topnews.data.repository.ArticleRemoteStorageImpl
import com.example.topnews.data.repository.ArticleRepository
import com.facebook.stetho.Stetho
import io.reactivex.annotations.NonNull
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class App : Application() {
	companion object {
		private lateinit var articleApi: ArticleApi
		private lateinit var repository: ArticleRepository
		private lateinit var articleDao: ArticleDao

		fun injectRepository() = repository
		fun injectArticleDao() = articleDao

	}

	private lateinit var retrofit: Retrofit

	override fun onCreate() {
		super.onCreate()

		AppCompatDelegate.setDefaultNightMode(
			AppCompatDelegate.MODE_NIGHT_YES
		)

		retrofit = Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.baseUrl("https://newsapi.org/v2/")
			.build()

		articleApi = retrofit.create(ArticleApi::class.java)

		val articleDatabase = AppDatabase.getInstance(applicationContext)

		articleDao = articleDatabase.articlesDao()
		val tagDao = articleDatabase.tagsDao()
		val tagArticleDao = articleDatabase.tagArticleDao()

		val remoteStorage = ArticleRemoteStorageImpl(articleApi)
		repository = ArticleRepository(tagDao, tagArticleDao, articleDao, remoteStorage)


		addStetho()

	}

	private fun addStetho() {
		if (BuildConfig.DEBUG){
			Stetho.initializeWithDefaults(this)
		}
	}



}