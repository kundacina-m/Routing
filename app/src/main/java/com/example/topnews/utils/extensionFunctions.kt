package com.example.topnews.utils

import android.annotation.SuppressLint
import android.text.format.DateUtils
import com.example.topnews.App
import com.example.topnews.R
import com.example.topnews.domain.RequestError.UnknownError
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants.API_TIME_FORMAT
import com.example.topnews.utils.Constants.DATE_ONLY_FORMAT
import com.example.topnews.utils.Constants.DAY_IN_MS
import com.example.topnews.utils.Constants.TIME_ONLY_FORMAT
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Mapping "Flowable type" incoming data or error to my WrappedResponse
fun <T> Flowable<T>.toSealed(): Flowable<WrappedResponse<T>> {

	return this.map<WrappedResponse<T>> { OnSuccess(it) }
		.onErrorReturn {
			OnError(UnknownError)
		}
}


// Mapping "Single type" incoming data or error to my WrappedResponse
fun <T> Single<T>.toSealed(): Single<WrappedResponse<T>> {

	return this.map<WrappedResponse<T>> { OnSuccess(it) }
		.onErrorReturn {
			OnError(UnknownError)
		}
}

// Mapping "Observable type" incoming data or error to my WrappedResponse
fun <T> Observable<T>.toSealed(): Observable<WrappedResponse<T>> {

	return this.map<WrappedResponse<T>> { OnSuccess(it) }
		.onErrorReturn {
			OnError(UnknownError)
		}
}

@SuppressLint("SimpleDateFormat")
fun String.fromStringISOtoDate(): Date =
	SimpleDateFormat(API_TIME_FORMAT).parse(this)

fun Date.asString(): String {

	val minPassed = (Date().time - this.time) / 60000

	return when {

		DateUtils.isToday(this.time + DAY_IN_MS) -> {
			val time = SimpleDateFormat(TIME_ONLY_FORMAT, Locale.getDefault()).format(this)
			"${App.getContext().getString(R.string.yesterday)} - $time"
		}

		!DateUtils.isToday(this.time) -> {
			SimpleDateFormat(DATE_ONLY_FORMAT, Locale.getDefault()).format(this)
		}

		minPassed >= 60 -> {
			val time = SimpleDateFormat(TIME_ONLY_FORMAT, Locale.getDefault()).format(this)
			"${App.getContext().getString(R.string.today)} - $time"
		}
		else -> "$minPassed ago"
	}
}