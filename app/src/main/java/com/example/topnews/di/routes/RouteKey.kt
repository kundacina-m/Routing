package com.example.topnews.di.routes

import base.BaseFragment
import dagger.MapKey
import kotlin.reflect.KClass

@Target(
	AnnotationTarget.FUNCTION,
	AnnotationTarget.PROPERTY_GETTER,
	AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class RouteKey(val fragment: KClass<out BaseFragment<*, *>>)