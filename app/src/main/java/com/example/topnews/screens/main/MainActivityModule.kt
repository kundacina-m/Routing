package com.example.topnews.screens.main

import com.example.topnews.di.modules.RouteModule
import com.example.topnews.di.scopes.ActivityScope
import com.example.topnews.di.scopes.FragmentScope
import com.example.topnews.screens.article.details.ArticleDetailsFragment
import com.example.topnews.screens.articlescategory.ArticlesCategoryFragment
import com.example.topnews.screens.newactivity.TestObj
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton
import kotlin.random.Random

@Module
abstract class MainActivityModule {

	@Module
	companion object {

		@JvmStatic
		@ActivityScope
		@Provides
		fun provideTest(): TestObj = TestObj()
	}


}