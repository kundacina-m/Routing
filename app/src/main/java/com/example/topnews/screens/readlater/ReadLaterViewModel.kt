package com.example.topnews.screens.readlater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.screens.Article
import com.example.topnews.utils.App

const val pageSize = 6

class ReadLaterViewModel : ViewModel() {

	private val repository = App.injectRepository()

	private var pages: Int = 0
	var endOfDB = false

	private var articles = MutableLiveData<List<Article>>()
	fun getFavouritesFromDB() = articles

	fun getArticlesFromDB() {
		val previousArticles = arrayListOf<Article>()
		articles.value?.let { previousArticles.addAll(articles.value!!) }

		repository.getArticlesPagination(
			previousArticles,
			pages * pageSize,
			(pages + 1) * pageSize
		) {
			endOfDB = it.size < (pages + 1) * pageSize
			articles.postValue(it)
			pages++
		}
	}

	fun removeWhenAllSelected(data: ArrayList<Article>) {
		val listToDelete = arrayListOf<Article>()
		repository.getAllLocal {
			for (element in it) {
				if (!data.contains(element)) {
					listToDelete.add(element)
				}
			}
			removeSelected(listToDelete)
		}
	}

	fun removeSelected(data: ArrayList<Article>) {
		for (article in data)
			repository.removeLocal(article) {
				if (it) {
					val array = ArrayList<Article>()
					array.addAll(articles.value?.asIterable()!!)
					articles.postValue(array - data)
				}
			}
	}

}