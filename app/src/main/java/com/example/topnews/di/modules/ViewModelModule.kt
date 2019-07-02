package com.example.topnews.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.topnews.di.ViewModelFactory
import com.example.topnews.di.ViewModelKey
import com.example.topnews.screens.article.details.ArticleDetailsViewModel
import com.example.topnews.screens.articlescategory.ArticlesCategoryViewModel
import com.example.topnews.screens.categories.itemsincategory.ItemsInCategoryViewModel
import com.example.topnews.screens.readlater.ReadLaterViewModel
import com.example.topnews.screens.search.SearchViewModel
import com.example.topnews.screens.topnews.TopNewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

	@Binds
	internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

	@Binds
	@IntoMap
	@ViewModelKey(TopNewsViewModel::class)
	internal abstract fun bindTopNewsViewModel(topNewsViewModel: TopNewsViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(ReadLaterViewModel::class)
	internal abstract fun bindReadLaterViewModel(readLaterViewModel: ReadLaterViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(SearchViewModel::class)
	internal abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(ItemsInCategoryViewModel::class)
	internal abstract fun bindItemsInCategoryViewModel(itemsInCategoryViewModel: ItemsInCategoryViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(ArticlesCategoryViewModel::class)
	internal abstract fun bindArticlesCateogryViewModel(articlesCategoryViewModel: ArticlesCategoryViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(ArticleDetailsViewModel::class)
	internal abstract fun bindArticleDetailsViewModel(articleDetailsViewModel: ArticleDetailsViewModel): ViewModel

}