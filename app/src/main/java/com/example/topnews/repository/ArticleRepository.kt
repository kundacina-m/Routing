package com.example.topnews.repository

import android.util.Log
import com.example.topnews.db.ArticleDaoImpl
import com.example.topnews.screens.Article
import com.example.topnews.screens.ResponseModel
import com.example.topnews.screens.search.pageSize
import com.example.topnews.utils.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository : IArticleRepository<Article> {

    private val articleDao by lazy {
        ArticleDaoImpl()
    }

    override fun addLocal(item: Article, func: (Boolean) -> Unit) {
        articleDao.insertItem(item).executeAsync {
            func.invoke(it)
        }
    }

    override fun removeLocal(item: Article, func: (Boolean) -> Unit) {
        articleDao.removeItem(item).executeAsync {
            func.invoke(it)
        }
    }

    override fun getLocal(id: String, func: (Article) -> Unit) {
    }

    override fun getAllLocal(func: (ArrayList<Article>) -> Unit) {

        articleDao.getAllItems().executeAsync {
            func.invoke(it as ArrayList<Article>)
        }
    }

    override fun addRemote(item: Article) {

    }

    override fun removeRemote(item: Article, func: (Boolean) -> Unit) {

    }

    override fun getRemote(id: String, func: (Article) -> Unit) {

    }

    override fun getAllRemote(func: (ArrayList<Article>) -> Unit) {
        App.injectApi().getArticles().enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("Failed", t.message)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.body()?.status == "ok")
                func.invoke(response.body()?.articles as ArrayList<Article>)
            }
        })
    }


    fun getArticlesPagination(old: ArrayList<Article>, from: Int, to: Int, func: (ArrayList<Article>) -> Unit) {

        articleDao.getArticlesFromTo(from, to).executeAsync {
            old.addAll(it)
            func.invoke(old)
        }
    }

    fun getArticlesByPages(query: String,pages: Int, func: (ArrayList<Article>, Int) -> Unit){
        App.injectApi().getArticlesForKeyword(query, pages, pageSize).enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("Failed", t.message)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.body()?.status == "ok")
                func.invoke(response.body()?.articles as ArrayList<Article>, response.body()?.totalResults!!)
            }
        })
    }

    fun getArticlesByCategory(category: String, func: (ArrayList<Article>) -> Unit){
        App.injectApi().getArticlesByCategory(category).enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d("Failed", t.message)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                func.invoke(response.body()?.articles as ArrayList<Article>)
            }
        })
    }

    fun checkIfArticleExistsInDB(article: Article, func: (Boolean) -> Unit){
        articleDao.checkIfArticleExists(article).executeAsync {
            func.invoke(it)
        }
    }

}