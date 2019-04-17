package com.example.topnews.data.networking

import com.example.topnews.data.networking.responses.ResponseArticle
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ArticleApi {

	@GET("top-headlines?country=us&apiKey=2c6b934ffa73493cb4ed77829d57ac63")
	fun getArticles(): Single<ResponseArticle>

	@GET("top-headlines?country=us&apiKey=2c6b934ffa73493cb4ed77829d57ac63")
	fun getItemsByQuery(@QueryMap parameters: Map<String, String>): Single<ResponseArticle>

}