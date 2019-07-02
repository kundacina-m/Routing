package com.example.topnews.screens.articlescategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import base.BaseViewModel
import com.example.topnews.data.db.Article
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.utils.Constants.PAGE_SIZE_CATEGORIES
import javax.inject.Inject

class ArticlesCategoryViewModel @Inject constructor(val repository: ArticleRepository) : BaseViewModel() {

	var onError = MutableLiveData<OnError<Nothing>>()
	lateinit var category: String

	private val dataSourceFactory: ArticleCategoryDataSourceFactory by lazy {
		ArticleCategoryDataSourceFactory(category, onError, repository)
	}

	val articles: LiveData<PagedList<Article>> by lazy {
		LivePagedListBuilder(dataSourceFactory, configurePagination()).build()
	}

	private fun configurePagination(): PagedList.Config =
		PagedList.Config.Builder()
			.setEnablePlaceholders(false)
			.setPrefetchDistance(1)
			.setPageSize(PAGE_SIZE_CATEGORIES)
			.build()

	override fun onCleared() {
		super.onCleared()
		dataSourceFactory.dataSource.value?.invalidate()
	}

}