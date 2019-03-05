package com.example.topnews.screens.frame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.MenuItemCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.topnews.R
import kotlinx.android.synthetic.main.activity_frame.*

class FrameActivity : AppCompatActivity() {

    private lateinit var navCtrl: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        navCtrl = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupBottomNavBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val searchItem = menu!!.findItem(R.id.search)
        defineSearchItemBehaviour(searchItem)

        return super.onCreateOptionsMenu(menu)

    }

    private fun defineSearchItemBehaviour(searchItem: MenuItem){

        MenuItemCompat.setOnActionExpandListener(searchItem, object: MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                if (navCtrl.currentDestination!!.id != R.id.searchFragment)
                    navCtrl.navigate(R.id.searchFragment)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                navCtrl.navigateUp()
                return true
            }
        })
    }

    private fun setupBottomNavBar(){
        bottom_navigation.setupWithNavController(navCtrl)
    }
}
