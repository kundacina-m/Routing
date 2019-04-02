package com.example.topnews.utils

import android.app.Application
import android.content.Context
import com.example.topnews.networking.ArticleApi
import com.example.topnews.repository.ArticleRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.example.topnews.sqlite.DBHelper

class App: Application() {
    companion object {
        private lateinit var articleApi: ArticleApi
        private lateinit var database: DBHelper
        private lateinit var repository : ArticleRepository

        fun injectDB() = database
        fun injectApi() = articleApi
        fun injectRepository() = repository
    }

    private lateinit var retrofit : Retrofit

    override fun onCreate() {
        super.onCreate()

        val appContext = applicationContext

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://newsapi.org/v2/")
            .build()

        articleApi = retrofit.create(ArticleApi::class.java)

        database = DBHelper(appContext)

        repository = ArticleRepository()

    }
}