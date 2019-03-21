package com.example.topnews.screens.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.utils.App
import com.example.topnews.screens.Article
import com.example.topnews.screens.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val pageSize = 6

class SearchViewModel : ViewModel() {
    private var pages: Int = 1
    var totalResults: Int = 0
    private lateinit var searchString: String
    private var articles = MutableLiveData<List<Article>>()
    fun getNetworkSearchResults() = articles

    fun getArticlesForQuery(searchText: String) {
        pages = 1
        articles.postValue(emptyList())
        getArticlesByPages(searchText)
    }

    fun getArticlesForQueryOnScroll() {
        getArticlesByPages()
    }

    private fun getArticlesByPages(searchText: String = searchString) {
        App.injectApi().getArticlesForKeyword(searchText, pages, pageSize).enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("Failed", t.message)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val array = ArrayList<Article>()
                articles.value?.let {
                    array.addAll(articles.value?.asIterable()!!)
                }

                array.addAll(response.body()!!.articles)

                totalResults = response.body()?.totalResults ?: 0

                articles.value = array
                searchString = searchText
                pages++
            }
        })
    }

}