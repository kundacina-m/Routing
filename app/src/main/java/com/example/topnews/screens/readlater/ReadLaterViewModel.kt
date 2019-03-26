package com.example.topnews.screens.readlater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.screens.Article
import com.example.topnews.db.ArticleDaoImpl

const val pageSize = 6

class ReadLaterViewModel : ViewModel() {

    private val articleDao by lazy {
        ArticleDaoImpl()
    }

    private var pages: Int = 0
    var endOfDB = false
    private var articles = MutableLiveData<List<Article>>()
    fun getFavouritesFromDB() = articles

    fun getArticlesFromDB() {

        val array = ArrayList<Article>()
        articles.value?.let {
            array.addAll(articles.value?.asIterable()!!)
        }

        articleDao.getArticlesFromTo(pages * pageSize, (pages + 1) * pageSize).executeAsync {
            endOfDB = it.size < pageSize
            array.addAll(it)

            articles.postValue(array)
            pages++
        }

    }

    fun removeItems(data: ArrayList<Article>) {

        for (article in data)
            articleDao.removeItem(article).executeAsync {

            }

        val array = ArrayList<Article>()
        array.addAll(articles.value?.asIterable()!!)
        articles.postValue(array - data)
    }

}