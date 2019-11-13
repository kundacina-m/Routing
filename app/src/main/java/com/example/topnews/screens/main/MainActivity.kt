package com.example.topnews.screens.main

import androidx.navigation.NavController
import androidx.navigation.Navigation
import base.BaseActivity
import com.example.topnews.R

class MainActivity : BaseActivity() {

	private val navCtrl: NavController by lazy {
		Navigation.findNavController(this, R.id.nav_host_fragment)
	}

	override fun getViewLayout(): Int = R.layout.activity_frame

	override fun initView() {}

	override fun onSupportNavigateUp(): Boolean =
		navCtrl.navigateUp()

}
