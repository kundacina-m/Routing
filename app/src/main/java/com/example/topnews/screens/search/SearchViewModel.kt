package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.rxkotlin.subscribeBy

const val pageSize = 6

class SearchViewModel : BaseViewModel() {
	private val repository = App.injectRepository()

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
		disposables.add(repository.getArticlesByPages(searchText, pages).subscribeBy { list ->
			val previousList = arrayListOf<Article>()
			articles.value?.let {
				previousList.addAll(it.asIterable())
			}
			previousList.addAll((list as OnSuccess).item)

			articles.value = previousList
			searchString = searchText
			pages++
		})
	}

}