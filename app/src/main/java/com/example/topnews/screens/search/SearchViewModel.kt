package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.rxkotlin.subscribeBy

const val pageSize = 6

class SearchViewModel : BaseViewModel() {
	private val repository = App.injectRepository()

	private var pages: Int = 1
	var totalResults: Int = 0
	private lateinit var searchString: String
	private var articles = MutableLiveData<WrappedResponse<List<Article>>>()
	fun getNetworkSearchResults() = articles

	fun getArticlesForQuery(searchText: String) {
		pages = 1
		articles.postValue(OnSuccess(emptyList()))
		getArticlesByPages(searchText)
	}

	fun getArticlesForQueryOnScroll() {
		getArticlesByPages()
	}

	private fun getArticlesByPages(searchText: String = searchString) {
		val previousList = arrayListOf<Article>()
		articles.value?.let {
			if (articles.value is OnSuccess) {
				previousList.addAll((articles.value as OnSuccess<List<Article>>).item)
			}
		}


		disposables.add(
			repository.getArticlesByPages(searchText, pages)
				.subscribeBy { list ->
					if (list is OnSuccess) {
						previousList.addAll(list.item)
						articles.postValue(OnSuccess(previousList))
						searchString = searchText
						pages++
					} else if (list is OnError){
						articles.postValue(list)
					}
				})
	}

}