package com.example.topnews.data.repository

import android.util.Log
import com.example.topnews.App
import com.example.topnews.data.db.ArticleDaoImpl
import com.example.topnews.data.model.Article
import com.example.topnews.data.networking.responses.ResponseArticle
import com.example.topnews.domain.IArticleRepository
import com.example.topnews.domain.OnWrappedResponse
import com.example.topnews.domain.RequestErrorParser
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.screens.search.pageSize
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository : IArticleRepository {

	private val articleDao by lazy {
		ArticleDaoImpl()
	}

	override fun addLocal(item: Article, onWrappedResponse: OnWrappedResponse<Boolean>) {
		articleDao.insertItem(item).executeAsync {
			onWrappedResponse(OnSuccess(it))
		}
	}

	override fun removeLocal(item: Article, onWrappedResponse: OnWrappedResponse<Boolean>) {
		articleDao.removeItem(item).executeAsync {
			onWrappedResponse(OnSuccess(it))
		}
	}

	override fun getLocal(id: String, onWrappedResponse: OnWrappedResponse<Article>) {}

	override fun getAllLocal(onWrappedResponse: OnWrappedResponse<List<Article>>) {

		articleDao.getAllItems().executeAsync {
			onWrappedResponse(OnSuccess(it as ArrayList<Article>))
		}
	}

	override fun addRemote(item: Article, onWrappedResponse: OnWrappedResponse<Boolean>) {}
	override fun removeRemote(item: Article, onWrappedResponse: OnWrappedResponse<Boolean>) {}
	override fun getRemote(id: String, onWrappedResponse: OnWrappedResponse<Article>) {}

	override fun getAllRemote(onWrappedResponse: OnWrappedResponse<List<Article>>) {
		App.injectApi().getArticles().enqueue(object : Callback<ResponseArticle> {
			override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
				Log.d("Failed", t.message)

				val requestError = RequestErrorParser.parse(t)
				onWrappedResponse(OnError(requestError))
			}

			override fun onResponse(call: Call<ResponseArticle>, res: Response<ResponseArticle>) {
				if (res.body()?.status == "ok")
					onWrappedResponse(OnSuccess(res.body()?.articles!!))
			}
		})
	}

	fun getArticlesPagination(old: ArrayList<Article>, from: Int, to: Int, onWrappedResponse:
	OnWrappedResponse<ArrayList<Article>>) {

		articleDao.getArticlesFromTo(from, to).executeAsync {
			old.addAll(it)
			onWrappedResponse(OnSuccess(old))
		}
	}

	fun getArticlesByPages(query: String, pages: Int, func: (ArrayList<Article>, Int) -> Unit) {
		App.injectApi().getArticlesForKeyword(query, pages, pageSize).enqueue(object : Callback<ResponseArticle> {
			override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
				Log.d("Failed", t.message)
			}

			override fun onResponse(call: Call<ResponseArticle>, response: Response<ResponseArticle>) {
				if (response.body()?.status == "ok")
					func.invoke(response.body()?.articles as ArrayList<Article>, response.body()?.totalResults!!)
			}
		})
	}

	fun getArticlesByCategory(category: String, onWrappedResponse: OnWrappedResponse<List<Article>>) {
		App.injectApi().getArticlesByCategory(category).enqueue(object : Callback<ResponseArticle> {
			override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
				Log.d("Failed", t.message)
			}

			override fun onResponse(call: Call<ResponseArticle>, response: Response<ResponseArticle>) {
				onWrappedResponse(OnSuccess(response.body()?.articles as ArrayList<Article>))
			}
		})
	}

	fun checkIfArticleExistsInDB(article: Article, wrappedResponse: OnWrappedResponse<Boolean>) {
			articleDao.checkIfArticleExists(article).executeAsync {
				wrappedResponse(OnSuccess(it))
		}
	}

}