package com.example.topnews.screens

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sqlite.ArticlesDBHelper

class ArticleViewModel : ViewModel() {

    private var articles = MutableLiveData<List<Article>>()
    fun getNetworkResults() = articles

    fun getArticles() {
        App.injectApi().getArticles().enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("Failed", t.message)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                articles.postValue(response.body()?.articles)
            }
        })
    }

    fun getArticlesFromCategory(category: String) {
        App.injectApi().getArticlesByCategory(category).enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("Failed", t.message)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                articles.postValue(response.body()?.articles)
            }
        })
    }

    fun getArticlesFromDB(context: Context){
        val DBHelper = ArticlesDBHelper(context)
        articles.postValue(DBHelper.readAllArticles())
    }

    fun getNumOfArticles(num: Int): MutableLiveData<List<Article>> {
        articles.value = FakeData.fetchSearchedData(num)
        return articles
    }
}