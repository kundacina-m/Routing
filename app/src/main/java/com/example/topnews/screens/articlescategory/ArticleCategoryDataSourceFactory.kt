package com.example.topnews.screens.articlescategory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import base.BaseDataSourceFactory
import com.example.topnews.data.db.Article
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse.OnError

class ArticleCategoryDataSourceFactory(
	private val category: String,
	val onError: MutableLiveData<OnError<Nothing>>,
	val repository: ArticleRepository
) :
	BaseDataSourceFactory<Article>() {

	override fun initDataSource(): DataSource<Int, Article> =
		ArticlesCategoryDataSource(category, onError, repository)
}