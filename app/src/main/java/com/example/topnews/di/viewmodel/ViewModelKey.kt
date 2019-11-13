package com.example.topnews.di.viewmodel

import androidx.lifecycle.ViewModel
import base.BaseFragment
import dagger.MapKey
import kotlin.reflect.KClass

@Target(
	AnnotationTarget.FUNCTION,
	AnnotationTarget.PROPERTY_GETTER,
	AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey(unwrapValue = false)
annotation class ViewModelKey(val fragment: KClass<out BaseFragment<*, *>>, val viewModel: KClass<out ViewModel>)