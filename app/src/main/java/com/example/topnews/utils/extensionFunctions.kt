package com.example.topnews.utils

import android.content.Context
import android.text.format.DateUtils
import com.example.topnews.R
import com.example.topnews.utils.Constants.API_TIME_FORMAT
import com.example.topnews.utils.Constants.DATE_ONLY_FORMAT
import com.example.topnews.utils.Constants.DAY_IN_MS
import com.example.topnews.utils.Constants.TIME_ONLY_FORMAT
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.fromStringISOtoDate(): Date =
	SimpleDateFormat(API_TIME_FORMAT, Locale.getDefault()).parse(this)

fun Date.asString(context: Context): String {

	val minPassed = (Date().time - this.time) / 60000

	return when {

		DateUtils.isToday(this.time + DAY_IN_MS) -> {
			val time = SimpleDateFormat(TIME_ONLY_FORMAT, Locale.getDefault()).format(this)
			"${context.getString(R.string.yesterday)} - $time"
		}

		!DateUtils.isToday(this.time) -> {
			SimpleDateFormat(DATE_ONLY_FORMAT, Locale.getDefault()).format(this)
		}

		minPassed >= 60 -> {
			val time = SimpleDateFormat(TIME_ONLY_FORMAT, Locale.getDefault()).format(this)
			"${context.getString(R.string.today)} - $time"
		}
		else -> "$minPassed ago"
	}
}