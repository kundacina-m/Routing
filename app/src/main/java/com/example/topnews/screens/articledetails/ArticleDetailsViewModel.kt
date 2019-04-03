package com.example.topnews.screens.articledetails

import androidx.lifecycle.ViewModel
import com.example.topnews.screens.Article
import com.example.topnews.utils.App

class ArticleDetailsViewModel : ViewModel() {

	var articleInDB = false
	private val repository = App.injectRepository()

	fun insertArticleInDb(article: Article, func: (Boolean) -> Unit) {
		repository.addLocal(article) {
			func.invoke(it)
		}
	}

	fun removeArticleFromDb(article: Article, func: (Boolean) -> Unit) {
		repository.removeLocal(article) {
			func.invoke(it)
		}
	}

	fun checkIfArticleExistsInDB(article: Article, func: (Boolean) -> Unit) {
		repository.checkIfArticleExistsInDB(article) {
			articleInDB = it
			func.invoke(it)
		}

	}
}