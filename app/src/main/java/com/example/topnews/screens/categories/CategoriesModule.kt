package com.example.topnews.screens.categories

import dagger.Module
import dagger.Provides

@Module
class CategoriesModule {

	@Provides
	fun providesCategoriesAdapter(fragment: CategoriesFragment) =
		CategoriesAdapter().apply {
			setData(categories)
			clickListener = fragment::onCategoryClick
			handleLiveData = fragment::bindAdapterToLiveData
		}

}