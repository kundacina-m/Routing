package com.example.topnews.screens.articlescategory

import dagger.Module
import dagger.Provides

@Module
class ArticlesCategoryModule {

	@Provides
	fun providesArticlesCategory(fragment: ArticlesCategoryFragment) =
		ArticlesCategoryAdapter().apply {
			clickListener = fragment::onItemClick
		}
}