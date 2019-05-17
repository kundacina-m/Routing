package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import base.BaseViewModel
import com.example.topnews.domain.WrappedResponse.OnError

class TopNewsViewModel : BaseViewModel() {

	var onError = MutableLiveData<OnError<Nothing>>()
	private val dataSourceFactory = TopNewsDataSourceFactory(onError)
	var articles = LivePagedListBuilder(dataSourceFactory, configurePagination()).build()

	private fun configurePagination(): PagedList.Config =
		PagedList.Config.Builder()
			.setEnablePlaceholders(false)
			.setPageSize(1)
			.build()

	override fun onCleared() {
		super.onCleared()
		dataSourceFactory.dataSource.value?.invalidate()
	}
}





