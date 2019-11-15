package com.example.topnews.screens.newactivity

import com.example.topnews.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewActivityModule {

	@FragmentScope
	@ContributesAndroidInjector(modules = [NewFragmentModule::class])
	abstract fun contributeNewFragmentInjector(): NewFragment
}