package com.example.topnews.screens.topnews

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import base.BaseDataSourceFactory
import com.example.topnews.App
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse.OnError

class TopNewsDataSourceFactory(private val onError: MutableLiveData<OnError<Nothing>>) :
	BaseDataSourceFactory<Article>() {

	override fun initDataSource(): DataSource<Int, Article> =
		TopNewsDataSource(onError)
}