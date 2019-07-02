package com.example.topnews

import androidx.appcompat.app.AppCompatDelegate
import com.crashlytics.android.Crashlytics
import com.example.topnews.di.components.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.fabric.sdk.android.Fabric

class NewsApplication : DaggerApplication() {

	override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
		DaggerAppComponent.factory().create(this)

	override fun onCreate() {
		super.onCreate()

		Fabric.with(this,Crashlytics())
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