package com.example.topnews.di.routes

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.topnews.R
import javax.inject.Inject

class NavigationControllerImpl @Inject constructor(private val activity: Activity) :
	NavigationController {

	override val route: NavController by lazy {  Navigation.findNavController(activity, R.id.nav_host_fragment)}

}