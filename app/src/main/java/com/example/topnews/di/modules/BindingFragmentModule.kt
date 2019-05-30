package com.example.topnews.di.modules

import com.example.topnews.screens.article.details.ArticleDetailsModule
import com.example.topnews.screens.articlescategory.ArticlesCategoryModule
import com.example.topnews.screens.categories.CategoriesModule
import com.example.topnews.screens.readlater.ReadLaterModule
import com.example.topnews.screens.search.SearchModule
import com.example.topnews.screens.topnews.TopNewsModule
import com.example.topnews.di.scopes.FragmentScope
import com.example.topnews.screens.article.details.ArticleDetailsFragment
import com.example.topnews.screens.articlescategory.ArticlesCategoryFragment
import com.example.topnews.screens.categories.CategoriesFragment
import com.example.topnews.screens.readlater.ReadLaterFragment
import com.example.topnews.screens.search.SearchFragment
import com.example.topnews.screens.topnews.TopNewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingFragmentModule {

	@FragmentScope
	@ContributesAndroidInjector(modules = [TopNewsModule::class])
	abstract fun contributeTopNewsFragmentInjector(): TopNewsFragment

	@FragmentScope
	@ContributesAndroidInjector(modules = [ReadLaterModule::class])
	abstract fun contributeReadLaterFragmentInjector(): ReadLaterFragment

	@FragmentScope
	@ContributesAndroidInjector(modules = [SearchModule::class])
	abstract fun contributeSearchFragmentInjector(): SearchFragment

	@FragmentScope
	@ContributesAndroidInjector(modules = [CategoriesModule::class])
	abstract fun contributeCategoriesFragmentInjector(): CategoriesFragment

	@FragmentScope
	@ContributesAndroidInjector(modules = [ArticleDetailsModule::class])
	abstract fun contributeArticleDetailsFragmentInjector(): ArticleDetailsFragment

	@FragmentScope
	@ContributesAndroidInjector(modules = [ArticlesCategoryModule::class])
	abstract fun contributeArticleCategoryFragmentInjector(): ArticlesCategoryFragment
}