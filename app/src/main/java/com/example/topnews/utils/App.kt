package com.example.topnews.utils

import android.app.Application
import android.content.Context
import com.example.topnews.networking.ArticleApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.example.topnews.sqlite.DBHelper

class App: Application() {
    companion object {
        private lateinit var articleApi: ArticleApi
        private lateinit var appContext: Context
        private lateinit var database: DBHelper

        fun injectDB() = database
        fun injectApi() = articleApi
        fun injectContext() = appContext
    }

    private lateinit var retrofit : Retrofit

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://newsapi.org/v2/")
            .build()

        articleApi = retrofit.create(ArticleApi::class.java)

        database = DBHelper(appContext)

    }
}