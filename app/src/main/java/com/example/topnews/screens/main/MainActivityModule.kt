package com.example.topnews.screens.main

import com.example.topnews.di.modules.RouteModule
import com.example.topnews.di.scopes.FragmentScope
import com.example.topnews.screens.article.details.ArticleDetailsFragment
import com.example.topnews.screens.articlescategory.ArticlesCategoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [RouteModule::class])
abstract class MainActivityModule {

	@FragmentScope
	@ContributesAndroidInjector
	abstract fun contributeArticleDetailsFragmentInjector(): ArticleDetailsFragment

	@FragmentScope
	@ContributesAndroidInjector
	abstract fun contributeArticleCategoryFragmentInjector(): ArticlesCategoryFragment
}