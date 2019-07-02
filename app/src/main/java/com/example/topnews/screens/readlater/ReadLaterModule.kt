package com.example.topnews.screens.readlater

import dagger.Module
import dagger.Provides

@Module
class ReadLaterModule {

	@Provides
	fun providesReadLaterAdapter(fragment: ReadLaterFragment, viewModel: ReadLaterViewModel): ReadLaterAdapter {
		return ReadLaterAdapter(viewModel).apply {
			clickListener = fragment::onItemClick
			handleMenu = fragment::showMenu
		}
	}
}