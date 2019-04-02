package com.example.topnews.screens.articlescategory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.utils.App
import com.example.topnews.screens.Article


class ArticlesCategoryViewModel: ViewModel() {

    private val repository =  App.injectRepository()

    private var articles = MutableLiveData<List<Article>>()
    fun getNetworkResults() = articles

    fun getArticlesFromCategory(category: String) {
        repository.getArticlesByCategory(category) {
        articles.postValue(it)
        }
    }
}