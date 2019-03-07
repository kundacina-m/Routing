package com.example.topnews.screens.frame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.topnews.R
import com.example.topnews.screens.search.SearchFragment
import kotlinx.android.synthetic.main.activity_frame.*

class FrameActivity : AppCompatActivity() {

    private lateinit var navCtrl: NavController
    private val waitingTimeForKeyDown = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        navCtrl = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupBottomNavBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val searchItem = menu!!.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        setSearchViewListener(searchView, setUpKeyNotPressedTimer())

        return super.onCreateOptionsMenu(menu)

    }

    private fun setSearchViewListener(searchView: SearchView, cntrForKeyUp: CountDownTimer) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                restartCountdownTimer(cntrForKeyUp)
                doOnTextChanged(newText!!)
                return false
            }

        })

    }

    private fun doOnTextChanged(newText: String) {
        if (navCtrl.currentDestination!!.id != R.id.searchFragment)
            navCtrl.navigate(R.id.searchFragment)
        if (newText.isEmpty())
            navCtrl.navigateUp()
    }

    private fun restartCountdownTimer(cntrForKeyUP: CountDownTimer) {
        cntrForKeyUP.cancel()
        cntrForKeyUP.start()
    }

    private fun setUpKeyNotPressedTimer(): CountDownTimer {
        return object : CountDownTimer(waitingTimeForKeyDown.toLong(), 500) {
            override fun onFinish() {
                val fragment = supportFragmentManager.findFragmentById(R.id.searchFragment) as SearchFragment
                fragment.myMethod()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }
    }

    private fun setupBottomNavBar() {
        bottom_navigation.setupWithNavController(navCtrl)
    }
}
