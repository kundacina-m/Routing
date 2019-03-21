package com.example.topnews.screens.topnews

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.utils.App
import com.example.topnews.screens.Article
import com.example.topnews.screens.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule

class TopNewsViewModel : ViewModel() {

    private var articles = MutableLiveData<List<Article>>()
    fun getNetworkResults() = articles

    fun getArticles() {
        App.injectApi().getArticles().enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("Failed", t.message)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                articles.postValue(response.body()?.articles)
                Timer().schedule(3000) {
                    articles.postValue((response.body()?.articles?.take(3)))
                }
                Timer().schedule(6000) {
                    articles.postValue((response.body()?.articles?.take(7)))
                }
                Timer().schedule(9000) {
                    articles.postValue(
                        listOf(
                            response.body()?.articles?.get(2)!!, response.body()?.articles?.get(4)!!,
                            response.body()?.articles?.get(5)!!, response.body()?.articles?.get(7)!!
                        )
                    )
                }
            }
        })
    }
}