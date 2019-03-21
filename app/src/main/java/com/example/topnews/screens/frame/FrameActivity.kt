package com.example.topnews.screens.frame

import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import base.BaseActivity
import com.example.topnews.R
import com.example.topnews.screens.search.SearchFragment
import kotlinx.android.synthetic.main.activity_frame.*

class FrameActivity : BaseActivity() {

    private val navCtrl: NavController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }
    private val waitingTimeForKeyDown = 1000
    private var numOfSearchedArticles: Int = 0
    private lateinit var menu: Menu

    private var searchTimer = object : SearchTimer(waitingTimeForKeyDown.toLong(), 500) {
        override fun onFinish() {
            updateSearchList()
        }
    }

    override fun getViewLayout(): Int = R.layout.activity_frame

    override fun initView() {
        setupBottomNavBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        this.menu = menu!!

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        setSearchViewListener(searchView)
        setupDestinationChangedLister()

        return super.onCreateOptionsMenu(menu)

    }

    private fun setSearchViewListener(searchView: SearchView) =
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                numOfSearchedArticles = newText!!.length
                restartCountdownTimer(searchTimer)
                doOnTextChanged(newText)
                return false
            }

        })

    private fun updateSearchList() =
        ((nav_host_fragment as NavHostFragment).childFragmentManager.primaryNavigationFragment as? SearchFragment)?.updateAdapter(
            numOfSearchedArticles
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


    private fun setupBottomNavBar() = bottom_navigation.setupWithNavController(navCtrl)

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            navCtrl.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDestinationChangedLister() =
        navCtrl.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.articleDetailsFragment -> {
                    bottom_navigation.visibility = View.GONE
                    menu.findItem(R.id.search).isVisible = false
                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                }
                R.id.searchFragment -> {
                    bottom_navigation.visibility = View.GONE
                }
                else -> {
                    bottom_navigation.visibility = View.VISIBLE
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                    menu.findItem(R.id.search).isVisible = true
                }
            }
        }
}
