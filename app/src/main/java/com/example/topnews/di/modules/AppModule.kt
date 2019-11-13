package com.example.topnews.di.modules

import android.content.Context
import com.example.topnews.NewsApplication
import com.example.topnews.data.networking.ArticleApi
import com.example.topnews.data.repository.ArticleRemoteStorageImpl
import com.example.topnews.data.repository.ArticleRepository
import com.example.topnews.di.viewmodel.ViewModelModule
import com.example.topnews.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

	@Provides
	fun provideContext(application: NewsApplication): Context =
		application.applicationContext


	@Singleton
	@Provides
	fun provideArticleApi(): ArticleApi =
		Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.baseUrl(Constants.API_BASE_URL)
			.build()
			.create(ArticleApi::class.java)

	@Singleton
	@Provides
	fun providesRemoteStorage(api: ArticleApi): ArticleRemoteStorageImpl =
		ArticleRemoteStorageImpl(api)

	@Singleton
	@Provides
	fun provideRepository(remoteStorage: ArticleRemoteStorageImpl): ArticleRepository =
		ArticleRepository(remoteStorage)

}