package com.example.topnews.utils

import com.example.topnews.domain.RequestError.UnknownError
import com.example.topnews.domain.WrappedResponse
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.sql.Timestamp
import java.text.SimpleDateFormat

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

fun String.fromISOtoTimestamp(): Timestamp {

	return Timestamp.valueOf(
		this.apply {
			replace("T", " ")
			replace("Z", "")
		}
	)
}

fun Timestamp.asString(style: String): String {

	val formattingStyle = SimpleDateFormat(style)
	return formattingStyle.format(this)

}
