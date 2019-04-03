package com.example.topnews.networking

import com.example.topnews.screens.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

	@GET("top-headlines?country=us&apiKey=2c6b934ffa73493cb4ed77829d57ac63")
	fun getArticles(): Call<ResponseModel>

	@GET("top-headlines?country=us&apiKey=2c6b934ffa73493cb4ed77829d57ac63")
	fun getArticlesByCategory(@Query("category") category: String): Call<ResponseModel>

	@GET("top-headlines?country=us&apiKey=2c6b934ffa73493cb4ed77829d57ac63")
	fun getArticlesForKeyword(@Query("q") query: String, @Query("page") page: Int, @Query("pageSize")
	pageSize: Int): Call<ResponseModel>

}