package com.example.topnews.screens.readlater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.screens.Article
import com.example.topnews.db.ArticleDaoImpl

const val pageSize = 6

class ReadLaterViewModel : ViewModel() {

    private val articlesDao by lazy {
        ArticleDaoImpl()
    }

    private fun getArticles(from: Int,to:Int) = articlesDao.getArticlesFromTo(from,to)

    private var pages: Int = 0
    var endOfDB = false
    private var articles = MutableLiveData<List<Article>>()
    fun getFavouritesFromDB() = articles

    fun getArticlesFromDB() {

        val array = ArrayList<Article>()
        articles.value?.let {
            array.addAll(articles.value?.asIterable()!!)
        }



        val dbList = getArticles(pages * pageSize, pageSize + (pages * pageSize)).toList()
        endOfDB = dbList.size < pageSize
        array.addAll(dbList)

        articles.value = array

        pages++
    }
}