package com.example.topnews.screens.search

import android.os.CountDownTimer

abstract class SearchTimer(time: Long, interval: Long) : CountDownTimer(time, interval) {

	override fun onTick(millisUntilFinished: Long) {}

}