package com.example.topnews

import com.example.topnews.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class NewsApplication : DaggerApplication() {

	override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
		DaggerAppComponent.factory().create(this)

}