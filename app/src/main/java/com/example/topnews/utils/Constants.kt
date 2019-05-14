package com.example.topnews.utils

object Constants {

	const val PARCEL_FOR_ARTICLE_DETAILS = "parcel"
	const val TRANSITION_ENABLED = "transition"
	const val ARG_CATEGORY = "category"

	// Api parameters
	const val API_BASE_URL = "https://newsapi.org/v2/"
	const val API_QUERY = "q"
	const val API_CATEGORY = "category"
	const val API_PAGE = "page"
	const val API_PAGESIZE = "pageSize"

	// Errors
	const val ERROR_UNKNOWN = "handleError: Unknown"
	const val ERROR_SERVER = "handleError: Server"
	const val ERROR_INTERNET = "handleError: Internet"
	const val ERROR_HTTP = "handleError: Http"

	// Time
	const val DATE_ONLY = "dd. MMMM yyyy - hh:mm a"
	const val TIME_ONLY = "hh:mm a"
	const val API_TIME_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'"
	const val DAY_IN_MS = 86400000

}