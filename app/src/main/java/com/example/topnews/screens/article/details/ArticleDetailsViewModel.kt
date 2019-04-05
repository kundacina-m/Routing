package com.example.topnews.screens.article.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.data.model.Article
import com.example.topnews.App
import com.example.topnews.domain.WrappedResponse.OnSuccess

class ArticleDetailsViewModel : ViewModel() {

	private val repository = App.injectRepository()

	var articleState = MutableLiveData<Boolean>()
	var isInDb = MutableLiveData<Boolean>()


	fun insertArticleInDb(article: Article) {
		repository.addLocal(article) {
			if (it is OnSuccess) {
				articleState.postValue(it.item)
				isInDb.postValue(it.item)
			}
		}
	}

	fun removeArticleFromDb(article: Article) {
		repository.removeLocal(article) {
			if (it is OnSuccess) {
				articleState.postValue(!it.item)
				isInDb.postValue(!it.item)
			}
		}
	}

	fun checkIfArticleExistsInDB(article: Article) {
		repository.checkIfArticleExistsInDB(article) {
			if (it is OnSuccess) {
				isInDb.postValue(it.item)
			}
		}

	}
}