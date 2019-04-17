package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.data.model.Article
import com.example.topnews.App

const val pageSize = 6

class SearchViewModel : ViewModel() {
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
		repository.getArticlesByPages(searchText, pages) { list, results ->
			val previousList = arrayListOf<Article>()
			articles.value?.let {
				previousList.addAll(it.asIterable())
			}
			previousList.addAll(list)

			totalResults = results
			articles.value = previousList
			searchString = searchText
			pages++
		}
	}

}