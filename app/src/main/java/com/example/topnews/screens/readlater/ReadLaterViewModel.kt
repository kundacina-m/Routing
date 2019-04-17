package com.example.topnews.screens.readlater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.App
import com.example.topnews.data.model.Article
import com.example.topnews.domain.WrappedResponse.OnSuccess

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
			if (it is OnSuccess) {
				endOfDB = it.item.size < (pages + 1) * pageSize
				articles.postValue(it.item)
				pages++
			}
		}
	}

	fun removeWhenAllSelected(data: ArrayList<Article>) {
		val listToDelete = arrayListOf<Article>()
		repository.getAllLocal {
			if (it is OnSuccess) {
				for (element in it.item) {
					if (!data.contains(element)) {
						listToDelete.add(element)
					}
				}
				removeSelected(listToDelete)
			}
		}
	}

	fun removeSelected(data: ArrayList<Article>) {
		for (article in data)
			repository.removeLocal(article) {
				if (it is OnSuccess && it.item) {
					val array = ArrayList<Article>()
					array.addAll(articles.value?.asIterable()!!)
					articles.postValue(array - data)
				}
			}
	}

}