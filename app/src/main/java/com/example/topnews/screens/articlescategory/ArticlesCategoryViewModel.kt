package com.example.topnews.screens.articlescategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import base.BaseViewModel
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError
import kotlin.properties.Delegates

class ArticlesCategoryViewModel : BaseViewModel() {

	var onError = MutableLiveData<OnError<Nothing>>()
	lateinit var category: String

	private val dataSourceFactory: ArticleCategoryDataSourceFactory by lazy {
		ArticleCategoryDataSourceFactory(category,onError)
	}

	val articles : LiveData<PagedList<Article>> by lazy {
		LivePagedListBuilder(dataSourceFactory, configurePagination()).build()
	}

	private fun configurePagination(): PagedList.Config =
		PagedList.Config.Builder()
			.setEnablePlaceholders(false)
			.setPrefetchDistance(1)
			.setPageSize(1)
			.build()

	override fun onCleared() {
		super.onCleared()
		dataSourceFactory.dataSource.value?.invalidate()
	}

}