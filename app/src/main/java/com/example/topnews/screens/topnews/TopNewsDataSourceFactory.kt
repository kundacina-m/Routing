package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.topnews.App
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError

class TopNewsDataSourceFactory(private val onError: MutableLiveData<OnError<Nothing>>) :
	DataSource.Factory<Int, Article>() {

	val articleLiveDataSource = MutableLiveData<TopNewsDataSource>()

	override fun create(): DataSource<Int, Article> {
		val articleDataSource = TopNewsDataSource(App.injectRepository(), onError)
		articleLiveDataSource.postValue(articleDataSource)
		return articleDataSource
	}
}