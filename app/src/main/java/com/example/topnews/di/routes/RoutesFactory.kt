package com.example.topnews.di.routes

import base.BaseFragment
import com.example.bookingagent.Routes
import com.example.topnews.di.scopes.ActivityScope
import javax.inject.Inject
import javax.inject.Provider

@ActivityScope
class RoutesFactory
@Inject
constructor(private val routes: Map<Class<out BaseFragment<*, *>>, @JvmSuppressWildcards Provider<Routes>>)
	: IRoutesFactory {

	override fun get(fragmentClass: Class<out BaseFragment<*, *>>): Routes? =
		routes[fragmentClass]?.get()
}