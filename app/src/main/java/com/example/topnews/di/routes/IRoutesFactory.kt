package com.example.topnews.di.routes

import base.BaseFragment
import com.example.bookingagent.Routes

interface IRoutesFactory {

	fun get(fragmentClass: Class<out BaseFragment<*, *>>): Routes?
}