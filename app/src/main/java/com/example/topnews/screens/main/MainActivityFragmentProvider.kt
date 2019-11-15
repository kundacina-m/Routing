package com.example.topnews.screens.main

import com.example.topnews.di.scopes.FragmentScope
import com.example.topnews.screens.article.details.ArticleDetailsFragment
import com.example.topnews.screens.articlescategory.ArticlesCategoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentProvider {

	@FragmentScope
	@ContributesAndroidInjector(modules = [DetailsModule::class])
	abstract fun contributeArticleDetailsFragmentInjector(): ArticleDetailsFragment

	@FragmentScope
	@ContributesAndroidInjector(modules = [CategoryModule::class])
	abstract fun contributeArticleCategoryFragmentInjector(): ArticlesCategoryFragment

}