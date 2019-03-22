package com.example.topnews.screens.articlescategory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.utils.App
import com.example.topnews.screens.Article
import com.example.topnews.screens.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesCategoryViewModel: ViewModel() {
    private var articles = MutableLiveData<List<Article>>()
    fun getNetworkResults() = articles

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
}