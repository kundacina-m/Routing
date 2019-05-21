package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import base.BaseDataSourceFactory
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError

class SearchDataSourceFactory(private val onError: MutableLiveData<OnError<Nothing>>) :
	BaseDataSourceFactory<Article>() {

	var query = ""

	override fun initDataSource(): DataSource<Int, Article> =
		SearchDataSource(onError, query)
}