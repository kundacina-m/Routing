package com.example.topnews.screens.categories

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.topnews.App
import com.example.topnews.data.db.Article
import com.example.topnews.domain.RequestError
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ItemsInCategoryViewModel : BaseViewModel() {

	private val liveData = mutableMapOf<String, MutableLiveData<WrappedResponse<List<Article>>>>()
	private val repos = App.injectRepository()

	fun fetchArticles(category: String) =
		disposables.add(
			repos.getArticlesByCategory(category)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeBy {

					if (it is OnSuccess && category!="General")
						liveData[category]?.postValue(it)
					else liveData[category]?.postValue(OnError(RequestError.ServerError))
				}
		)

	fun initLiveData(category: String): MutableLiveData<WrappedResponse<List<Article>>> {
		if (!liveData.containsKey(category))
			liveData[category] = MutableLiveData()
		return liveData[category]!!
	}
}