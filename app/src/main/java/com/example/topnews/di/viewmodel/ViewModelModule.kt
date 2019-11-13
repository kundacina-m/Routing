package com.example.topnews.di.viewmodel

import androidx.lifecycle.ViewModel
import com.example.topnews.screens.article.details.ArticleDetailsFragment
import com.example.topnews.screens.article.details.ArticleDetailsViewModel
import com.example.topnews.screens.articlescategory.ArticlesCategoryFragment
import com.example.topnews.screens.articlescategory.ArticlesCategoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

	@Binds
	internal abstract fun bindViewModelFactory(factory: ViewModelFactory): IViewModelFactory

	@Binds
	@IntoMap
	@ViewModelKey(ArticlesCategoryFragment::class, ArticlesCategoryViewModel::class)
	internal abstract fun bindArticlesCateogryViewModel(articlesCategoryViewModel: ArticlesCategoryViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(ArticleDetailsFragment::class, ArticleDetailsViewModel::class)
	internal abstract fun bindArticlesDetailsViewModel(articleDetailsViewModel: ArticleDetailsViewModel): ViewModel
}