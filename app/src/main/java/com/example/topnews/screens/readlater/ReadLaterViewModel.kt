package com.example.topnews.screens.readlater

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.db.Article
import com.example.topnews.utils.Constants.PAGE_SIZE_READ_LATER
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ReadLaterViewModel : BaseViewModel() {

	private val repository = App.injectRepository()

	private var articles: LiveData<PagedList<Article>> =
		LivePagedListBuilder(repository.getArticlesPagination(), configurePagination()).build()

	fun getArticles() = articles

	fun addTagToArticle(articleId: String, tag: String) {
		repository.addTag(articleId, tag)
	}

	private fun configurePagination(): PagedList.Config =
		PagedList.Config.Builder()
			.setEnablePlaceholders(false)
			.setPrefetchDistance(1)
			.setInitialLoadSizeHint(PAGE_SIZE_READ_LATER)
			.setPageSize(PAGE_SIZE_READ_LATER)
			.build()

	fun removeSelected(checkedArticles: ArrayList<Article>) {
		for (article in checkedArticles)
			disposables.add(
				Single.just(repository)
					.subscribeOn(Schedulers.io())
					.subscribeBy {
						it.removeFromDB(article)
					}
			)
	}

}