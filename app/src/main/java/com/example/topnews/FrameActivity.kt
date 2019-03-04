package com.example.topnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_frame.*

class FrameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        val navCtrl = Navigation.findNavController(this,R.id.nav_host_fragment)
        setupBottomNavBar(navCtrl)
    }

    private fun setupBottomNavBar(navCtrl: NavController){
        bottom_navigation.setupWithNavController(navCtrl)
    }
}
