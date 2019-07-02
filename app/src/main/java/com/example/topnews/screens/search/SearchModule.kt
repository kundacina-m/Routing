package com.example.topnews.screens.search

import dagger.Module
import dagger.Provides

@Module
class SearchModule {

	@Provides
	fun providesSearchAdapter(fragment: SearchFragment): SearchAdapter {
		return SearchAdapter().apply {
			clickListener = fragment::onItemClick
		}
	}

}