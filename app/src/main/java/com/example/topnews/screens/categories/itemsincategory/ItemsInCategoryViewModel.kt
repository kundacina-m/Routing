package com.example.topnews.screens.categories.itemsincategory

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.data.db.Article
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.utils.Constants.PAGE_SIZE_ITEMS_WITHIN_CATEGORY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

const val pages = 1

class ItemsInCategoryViewModel @Inject constructor(val repository: ArticleRepository) : BaseViewModel() {

	private val liveData = mutableMapOf<String, MutableLiveData<WrappedResponse<List<Article>>>>()

	fun fetchArticles(category: String) =
		disposables.add(
			repository.getArticlesByCategory(
				category,
				pages, PAGE_SIZE_ITEMS_WITHIN_CATEGORY
			)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeBy {
					liveData[category]?.postValue(it)
				}
		)

	fun initLiveData(category: String): MutableLiveData<WrappedResponse<List<Article>>> {
		if (!liveData.containsKey(category))
			liveData[category] = MutableLiveData()
		return liveData[category]!!
	}
}