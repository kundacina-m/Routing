package com.example.topnews.screens.topnews

import dagger.Module
import dagger.Provides

@Module
class TopNewsModule {


	@Provides
	fun providesTopNewsAdapter(fragment: TopNewsFragment): TopNewsAdapter {
		return TopNewsAdapter().apply {
			onClickWithTransition = fragment::onItemClickWithImgTransition
		}
	}

}