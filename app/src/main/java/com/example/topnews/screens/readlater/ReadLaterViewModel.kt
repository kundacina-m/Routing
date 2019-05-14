package com.example.topnews.screens.readlater

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.db.Article

private const val TAG = "ReadLaterViewModel"

class ReadLaterViewModel : BaseViewModel() {

	private val repository = App.injectRepository()

	private val pagedListConfig = PagedList.Config.Builder()
		.setEnablePlaceholders(false)
		.setInitialLoadSizeHint(1)
		.setPageSize(1)
		.build()

	private var articles: LiveData<PagedList<Article>> =
		LivePagedListBuilder(repository.getArticlesPagination(), pagedListConfig).build()

	fun getArticles() = articles

	fun addTagToArticle(articleId: String, tag: String) {
		repository.addTag(articleId, tag)
	}

}