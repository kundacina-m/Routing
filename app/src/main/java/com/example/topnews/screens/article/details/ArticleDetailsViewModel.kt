package com.example.topnews.screens.article.details

import android.util.Log
import base.BaseViewModel
import com.example.topnews.screens.newactivity.TestObj
import javax.inject.Inject

class ArticleDetailsViewModel @Inject constructor(val testObj: TestObj) : BaseViewModel() {
	init {
		Log.d("ArticlesCategoryVM", testObj.toString())

	}
}