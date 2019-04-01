package com.example.topnews.screens.frame

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import base.BaseActivity
import com.example.topnews.R
import com.example.topnews.screens.Article
import com.example.topnews.screens.search.SearchFragment
import com.example.topnews.utils.Constants
import kotlinx.android.synthetic.main.activity_frame.*

class FrameActivity : BaseActivity() {

    private val navCtrl: NavController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }
    private val waitingTimeForKeyDown = 1000
    private var searchKeyword: String = ""
    private lateinit var menu: Menu

    var voidSelection: ((Unit) -> Boolean?)? = null


    private var searchTimer = object : SearchTimer(waitingTimeForKeyDown.toLong(), 500) {
        override fun onFinish() {
            updateSearchList()
        }
    }

    override fun getViewLayout(): Int = R.layout.activity_frame

    override fun initView() {
        setupBottomNavBar()
        checkIfStartedFromWidget()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
      //  menuInflater.inflate(R.menu.search, menu)
//        this.menu = menu!!
//
//        val searchItem = menu.findItem(R.id.search)
//
//        setupDestinationChangedLister()
//
//        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {
//            override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true
//            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
//                navCtrl.navigateUp()
//                return true
//            }
//
//        })
//
//        val searchView = searchItem.actionView as SearchView
//        setSearchViewListener(searchView)

        return super.onCreateOptionsMenu(menu)

    }

    private fun checkIfStartedFromWidget(){
        val intent = intent
        val article = intent?.extras?.getParcelable<Article>(Constants.PARCEL_FOR_ARTICLE_DETAILS)

        article?.let {
            navCtrl.navigate(R.id.articleDetailsFragment, Bundle().apply { putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS,article) })
        }
    }

    private fun setSearchViewListener(searchView: SearchView) =
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return if (!newText?.isEmpty()!!) {
                        searchKeyword = newText
                        restartCountdownTimer(searchTimer)
                        doOnTextChanged(newText)
                        false
                    } else true
                }

            })
            setOnSearchClickListener {
                navCtrl.navigate(R.id.searchFragment)
            }
        }

    private fun updateSearchList() =
        ((nav_host_fragment as NavHostFragment).childFragmentManager.primaryNavigationFragment as? SearchFragment)?.fetchData(
            searchKeyword
        )

    private fun doOnTextChanged(newText: String) {
        if (navCtrl.currentDestination!!.id != R.id.searchFragment)
            navCtrl.navigate(R.id.searchFragment)
        if (newText.isEmpty())
            navCtrl.navigateUp()
    }

    private fun restartCountdownTimer(cntrForKeyUP: CountDownTimer) = cntrForKeyUP.apply {
        cancel()
        start()
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
