package com.example.topnews.screens.readlater

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.rxkotlin.subscribeBy

const val pageSize = 6

class ReadLaterViewModel : BaseViewModel() {

	private val repository = App.injectRepository()

	private var pages: Int = 0
	var endOfDB = false

	private var articles = MutableLiveData<WrappedResponse<List<Article>>>()
	fun getFavouritesFromDB() = articles

	fun getArticlesFromDB() {

		disposables.add(repository.getAllLocal().subscribeBy {
			articles.postValue(it)
		})




//		val previousArticles = getListFromLiveData()
//
//		disposables.add(repository.getArticlesPagination(pages * pageSize, (pages + 1) * pageSize).subscribeBy {
//			if (it is OnSuccess) {
//				endOfDB = it.item.size < (pages + 1) * pageSize
//				articles.postValue(OnSuccess(previousArticles + it.item))
//				pages++
//			}
//		})
	}

	fun removeWhenAllSelected(data: ArrayList<Article>) {
		val listToDelete = arrayListOf<Article>()
		disposables.add(repository.getAllLocal().subscribeBy {
			if (it is OnSuccess) {
				for (element in it.item) {
					if (!data.contains(element)) {
						listToDelete.add(element)
					}
				}
				removeSelected(listToDelete)
			}
		})
	}

	fun removeSelected(data: ArrayList<Article>) {
//		for (article in data)
//			disposables.add(repository.removeLocal(article).subscribeBy {
//				if (it is OnSuccess && it.item) {
//					val array = getListFromLiveData()
//					articles.postValue(OnSuccess(array - data))
//				}
//			})
	}

	private fun getListFromLiveData(): ArrayList<Article> {
		val array = ArrayList<Article>()
		articles.value?.let {
			if (articles.value is OnSuccess) {
				array.addAll((articles.value as OnSuccess<List<Article>>).item)
			}
		}
		return array
	}

}