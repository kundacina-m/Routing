package com.example.topnews.di.modules

import com.example.topnews.di.scopes.ActivityScope
import com.example.topnews.di.viewmodel.ViewModelModule
import com.example.topnews.screens.main.MainActivity
import com.example.topnews.screens.main.MainActivityFragmentProvider
import com.example.topnews.screens.main.MainActivityModule
import com.example.topnews.screens.newactivity.NewActivity
import com.example.topnews.screens.newactivity.NewActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesInjector {

	@ActivityScope
	@ContributesAndroidInjector(
		modules = [
			MainActivityModule::class,
			RouteModule::class,
			MainActivityFragmentProvider::class,
			ViewModelModule::class
		]
	)
	abstract fun contributeMainActivityInjector(): MainActivity

	@ActivityScope
	@ContributesAndroidInjector(modules = [NewActivityModule::class])
	abstract fun contributeNewActivityInjector(): NewActivity

}