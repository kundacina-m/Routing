package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.screens.topnews.TopNewsDataSourceFactory
import io.reactivex.rxkotlin.subscribeBy

const val pageSize = 6

class SearchViewModel : BaseViewModel() {

	var onError = MutableLiveData<OnError<Nothing>>()
	private val dataSourceFactory = SearchDataSourceFactory(onError)
	var articles = LivePagedListBuilder(dataSourceFactory, configurePagination()).build()

	private fun configurePagination(): PagedList.Config =
		PagedList.Config.Builder()
			.setEnablePlaceholders(false)
			.setPageSize(1)
			.build()

	fun queryForString(query: String) {
		dataSourceFactory
	}

	override fun onCleared() {
		super.onCleared()
		dataSourceFactory.articleLiveDataSource.value?.invalidate()
	}
}