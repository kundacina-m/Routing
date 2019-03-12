package com.example.topnews.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleViewModel: ViewModel() {

    private var articles = MutableLiveData<List<Article>>()

    fun getArticles(): MutableLiveData<List<Article>> {
        articles.value = FakeData.fetchData()
        return articles
    }

    fun getNumOfArticles(num: Int): MutableLiveData<List<Article>>{
        articles.value = FakeData.fetchSearchedData(num)
        return articles
    }
}