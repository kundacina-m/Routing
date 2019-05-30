package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import base.BaseViewModel
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.utils.Constants.PAGE_SIZE_TOP_NEWS
import javax.inject.Inject

class TopNewsViewModel @Inject constructor(val repository: ArticleRepository) : BaseViewModel() {

	var onError = MutableLiveData<OnError<Nothing>>()
	var loading = MutableLiveData<Boolean>()
	private val dataSourceFactory = TopNewsDataSourceFactory(onError, loading, repository)
	var articles = LivePagedListBuilder(dataSourceFactory, configurePagination()).build()

	private fun configurePagination(): PagedList.Config =
		PagedList.Config.Builder()
			.setEnablePlaceholders(false)
			.setPrefetchDistance(1)
			.setPageSize(PAGE_SIZE_TOP_NEWS)
			.build()

	override fun onCleared() {
		super.onCleared()
		dataSourceFactory.dataSource.value?.invalidate()
	}
}





