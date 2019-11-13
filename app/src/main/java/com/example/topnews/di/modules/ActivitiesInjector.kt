package com.example.topnews.di.modules

import com.example.topnews.di.scopes.ActivityScope
import com.example.topnews.screens.main.MainActivity
import com.example.topnews.screens.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesInjector {

	@ActivityScope
	@ContributesAndroidInjector(modules = [MainActivityModule::class])
	abstract fun contributeMainActivityInjector(): MainActivity

}