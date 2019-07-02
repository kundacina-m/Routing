package com.example.topnews.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import base.BaseDataSourceFactory
import com.example.topnews.data.db.Article
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse.OnError
import javax.inject.Inject

class SearchDataSourceFactory @Inject constructor(
	private val onError: MutableLiveData<OnError<Nothing>>,
	val repository: ArticleRepository
) : BaseDataSourceFactory<Article>() {

	var query = ""

	override fun initDataSource(): DataSource<Int, Article> =
		SearchDataSource(onError, query, repository)
}