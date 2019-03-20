package com.example.topnews.screens

import android.app.Application
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {
    companion object {
        private lateinit var articleApi: ArticleApi
        fun injectApi() = articleApi
    }

    private lateinit var retrofit : Retrofit

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://newsapi.org/v2/")
            .build()

        articleApi = retrofit.create(ArticleApi::class.java)

    }
}