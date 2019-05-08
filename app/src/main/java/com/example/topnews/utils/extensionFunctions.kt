package com.example.topnews.utils

import com.example.topnews.domain.RequestError.UnknownError
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants.API_TIME_FORMAT
import com.example.topnews.utils.Constants.DATE_ONLY
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.abs

fun <T> Flowable<T>.toSealed(): Flowable<WrappedResponse<T>> {

	return this.map<WrappedResponse<T>> { OnSuccess(it) }
		.onErrorReturn {
			OnError(UnknownError)
		}
}

fun <T> Single<T>.toSealed(): Single<WrappedResponse<T>> {

	return this.map<WrappedResponse<T>> { OnSuccess(it) }
		.onErrorReturn {
			OnError(UnknownError)
		}
}

fun <T> Observable<T>.toSealed(): Observable<WrappedResponse<T>> {

	return this.map<WrappedResponse<T>> { OnSuccess(it) }
		.onErrorReturn {
			OnError(UnknownError)
		}
}

fun String.fromStringISOtoDate() : Date {

	val formatter = SimpleDateFormat(API_TIME_FORMAT)
	return formatter.parse(this)
}

fun Date.asString(style: String): String {

	val minPassed = (Date().time-this.time)/60000

	return when {
		minPassed>60*24 -> {
			val formattingStyle = SimpleDateFormat(style)
			formattingStyle.format(this)
		}
		minPassed>60 -> "${minPassed/60} hours ${minPassed%60} minutes ago"
		else -> "$minPassed ago"
	}
}
