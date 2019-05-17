package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import base.BaseViewModel
import com.example.topnews.domain.WrappedResponse.OnError

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
		dataSourceFactory.query = query
		invalidateDataSource()
	}

	override fun onCleared() {
		super.onCleared()
		invalidateDataSource()
	}

	private fun invalidateDataSource() =
		dataSourceFactory.dataSource.value?.invalidate()
}