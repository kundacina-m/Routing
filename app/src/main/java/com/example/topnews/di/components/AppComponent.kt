package com.example.topnews.di.components

import com.example.topnews.NewsApplication
import com.example.topnews.di.ViewModelFactory
import com.example.topnews.di.modules.BindingActivityModule
import com.example.topnews.di.modules.AppModule
import com.example.topnews.di.modules.BindingFragmentModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
	modules = [
		AppModule::class,
		AndroidSupportInjectionModule::class,
		BindingActivityModule::class,
		BindingFragmentModule::class
	]
)
interface AppComponent : AndroidInjector<NewsApplication> {

	@Component.Factory
	abstract class Factory : AndroidInjector.Factory<NewsApplication>

}