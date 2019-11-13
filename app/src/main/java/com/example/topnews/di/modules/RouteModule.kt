package com.example.topnews.di.modules

import android.app.Activity
import com.example.bookingagent.Routes
import com.example.topnews.di.routes.IRoutesFactory
import com.example.topnews.di.routes.NavigationController
import com.example.topnews.di.routes.NavigationControllerImpl
import com.example.topnews.di.routes.RouteKey
import com.example.topnews.di.routes.RoutesFactory
import com.example.topnews.screens.article.details.ArticleDetailsFragment
import com.example.topnews.screens.article.details.ArticleDetailsRoutes
import com.example.topnews.screens.articlescategory.ArticlesCategoryFragment
import com.example.topnews.screens.articlescategory.ArticlesCategoryRoute
import com.example.topnews.screens.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RouteModule {

	@Binds
	internal abstract fun bindActivity(mainActivity: MainActivity): Activity

	@Binds
	internal abstract fun bindNavController(navigationControllerImpl: NavigationControllerImpl): NavigationController

	@Binds
	internal abstract fun bindFactory(routesModelFactory: RoutesFactory): IRoutesFactory

	@Binds
	@IntoMap
	@RouteKey(ArticlesCategoryFragment::class)
	internal abstract fun bindArticlesCategoryRoutes(articlesCategoryRoute: ArticlesCategoryRoute): Routes

	@Binds
	@IntoMap
	@RouteKey(ArticleDetailsFragment::class)
	internal abstract fun bindArticleDetailsRoutes(articleDetailsRoutes: ArticleDetailsRoutes): Routes

}
