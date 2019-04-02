package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.utils.App
import com.example.topnews.screens.Article


class TopNewsViewModel : ViewModel() {

    private val repository = App.injectRepository()

    private var articles = MutableLiveData<List<Article>>()
    fun getNetworkResults() = articles

    fun getArticles() {
        repository.getAllRemote {
            articles.postValue(it)
        }
    }
}