package com.example.topnews

import androidx.appcompat.app.AppCompatDelegate
import com.example.topnews.di.components.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class NewsApplication : DaggerApplication() {

	override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
		DaggerAppComponent.factory().create(this)

	override fun onCreate() {
		super.onCreate()

		handleTheme()
		setupStetho()
	}

	private fun handleTheme() {

		AppCompatDelegate.setDefaultNightMode(
			AppCompatDelegate.MODE_NIGHT_YES
		)
	}

	private fun setupStetho() {

		if (BuildConfig.DEBUG) {
			Stetho.initializeWithDefaults(this)
		}
	}

}