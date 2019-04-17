package com.example.topnews.screens.frame

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import base.BaseActivity
import com.example.topnews.R
import com.example.topnews.data.model.Article
import com.example.topnews.utils.Constants
import kotlinx.android.synthetic.main.activity_frame.bottom_navigation

class FrameActivity : BaseActivity() {

	private val navCtrl: NavController by lazy {
		Navigation.findNavController(this, R.id.nav_host_fragment)
	}

	var voidSelection: ((Unit) -> Boolean?)? = null

	override fun getViewLayout(): Int = R.layout.activity_frame

	override fun initView() {
		setupBottomNavBar()
		checkIfStartedFromWidget()

	}

	private fun checkIfStartedFromWidget() {
		val intent = intent
		val article = intent?.extras?.getParcelable<Article>(Constants.PARCEL_FOR_ARTICLE_DETAILS)

		article?.let {
			navCtrl.navigate(R.id.articleDetailsFragment,
				Bundle().apply { putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS, article) })
		}
	}

	private fun setupBottomNavBar() {
		bottom_navigation.setupWithNavController(navCtrl)
		bottom_navigation.setOnNavigationItemReselectedListener { }
	}

	override fun onSupportNavigateUp(): Boolean {
		return navCtrl.navigateUp()
	}

	override fun onBackPressed() {
		if (navCtrl.currentDestination?.id == R.id.readLaterFragment) {
			if (!voidSelection?.invoke(Unit)!!) {
				navCtrl.navigateUp()
			}
		} else navCtrl.navigateUp()
	}

}
