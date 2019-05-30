package com.example.topnews.di.modules

import com.example.topnews.di.scopes.ActivityScope
import com.example.topnews.screens.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingActivityModule {

	@ActivityScope
	@ContributesAndroidInjector(modules = [BindingFragmentModule::class])
	abstract fun contributeMainActivityInjector(): MainActivity
}