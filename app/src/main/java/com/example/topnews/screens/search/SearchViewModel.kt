package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import base.BaseViewModel
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.utils.Constants.PAGE_SIZE_SEARCH

class SearchViewModel : BaseViewModel() {

	var query = MutableLiveData<String>()
	var onError = MutableLiveData<OnError<Nothing>>()
	private val dataSourceFactory = SearchDataSourceFactory(onError)
	var articles = LivePagedListBuilder(dataSourceFactory, configurePagination()).build()

	private fun configurePagination(): PagedList.Config =
		PagedList.Config.Builder()
			.setEnablePlaceholders(false)
			.setPrefetchDistance(1)
			.setPageSize(PAGE_SIZE_SEARCH)
			.build()

	fun queryForString(query: String) {
		this.query.postValue(query)
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