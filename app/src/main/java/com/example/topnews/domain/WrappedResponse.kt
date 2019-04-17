package com.example.topnews.domain

import com.example.topnews.domain.RequestError.HttpError
import com.example.topnews.domain.RequestError.NoInternetError
import com.example.topnews.domain.RequestError.UnknownError
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.HttpException
import java.net.UnknownHostException

typealias OnWrappedResponse<T> = (WrappedResponse<T>) -> Unit

sealed class WrappedResponse<out T> {

	data class OnSuccess<out T>(val item: T) : WrappedResponse<T>()
	data class OnError<out T >(val error: RequestError) : WrappedResponse<T>()

}

sealed class RequestError {
	object UnknownError : RequestError()
	object NoInternetError : RequestError()
	object ServerError : RequestError()
	data class HttpError(val code: Int, val message: String) : RequestError()
}

object RequestErrorParser {
	fun parse(t: Throwable): RequestError {

		return when (t) {
			is HttpException -> HttpError(t.code(), t.message())
			is UnknownHostException -> NoInternetError
			else -> UnknownError
		}
	}
}