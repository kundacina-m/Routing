package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.topnews.App
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError

class SearchDataSourceFactory(private val onError: MutableLiveData<OnError<Nothing>>) :
	DataSource.Factory<Int, Article>() {

	val articleLiveDataSource = MutableLiveData<SearchDataSource>()
	var query = ""

	override fun create(): DataSource<Int, Article> {
		val articleDataSource = SearchDataSource(App.injectRepository(), onError)
		articleLiveDataSource.postValue(articleDataSource)
		return articleDataSource
	}
}