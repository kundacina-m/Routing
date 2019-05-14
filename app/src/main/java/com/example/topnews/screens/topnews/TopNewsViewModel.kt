package com.example.topnews.screens.topnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import base.BaseViewModel
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError

class TopNewsViewModel : BaseViewModel() {

	var articlePagedList: LiveData<PagedList<Article>>
	var onError = MutableLiveData<OnError<Nothing>>()

	init {

		val itemDataSourceFactory = ArticleDataSourceFactory(onError)
		articlePagedList = LivePagedListBuilder(itemDataSourceFactory, configurePagination()).build()
	}

	private fun configurePagination(): PagedList.Config =
		PagedList.Config.Builder()
			.setEnablePlaceholders(false)
			.setPageSize(1)
			.build()

}





