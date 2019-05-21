package com.example.topnews.screens.categories.itemsincategory

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.db.Article
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.utils.Constants.PAGE_SIZE_ITEMS_WITHIN_CATEGORY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

const val pages = 1

class ItemsInCategoryViewModel : BaseViewModel() {

	private val liveData = mutableMapOf<String, MutableLiveData<WrappedResponse<List<Article>>>>()
	private val repos = App.injectRepository()

	fun fetchArticles(category: String) =
		disposables.add(
			repos.getArticlesByCategory(category,
				pages, PAGE_SIZE_ITEMS_WITHIN_CATEGORY)
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