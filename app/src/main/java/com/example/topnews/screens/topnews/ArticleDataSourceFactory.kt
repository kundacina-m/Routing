package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.topnews.App
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError

class ArticleDataSourceFactory(private val onError: MutableLiveData<OnError<Nothing>>) :
	DataSource.Factory<Int, Article>() {

	val articleLiveDataSource = MutableLiveData<ArticleDataSource>()

	override fun create(): DataSource<Int, Article> {
		val articleDataSource = ArticleDataSource(App.injectRepository(), onError)
		articleLiveDataSource.postValue(articleDataSource)
		return articleDataSource
	}
}