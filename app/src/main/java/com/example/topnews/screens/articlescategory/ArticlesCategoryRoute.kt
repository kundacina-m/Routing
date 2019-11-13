package com.example.topnews.screens.articlescategory

import com.example.bookingagent.Routes
import com.example.topnews.data.Article
import com.example.topnews.di.routes.NavigationController
import javax.inject.Inject

class ArticlesCategoryRoute @Inject constructor(val navigationController: NavigationController) :
	Routes() {

	fun goToDetails(dataItem: Article, option1: String, option2: String, list: ArrayList<Int>) =
		ArticlesCategoryFragmentDirections.actionArticlesCategoryFragmentToArticleDetailsFragment(
			article = dataItem,
			firstString = option1,
			secondString = option2,
			list = list.toIntArray()
		).run {
			navigationController.route.navigate(this)
		}
}
