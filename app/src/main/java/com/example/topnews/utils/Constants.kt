package com.example.topnews.utils

object Constants {

	const val PARCEL_FOR_ARTICLE_DETAILS = "parcel"
	const val TRANSITION_ENABLED = "transition"
	const val ARG_CATEGORY = "category"
	const val SHOW_NAV_BAR = "nav_bar"

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
	const val ERROR_DATABASE = "handleError: Database"

	// Time
	const val DATE_ONLY_FORMAT = "dd. MMMM yyyy - hh:mm a"
	const val TIME_ONLY_FORMAT = "hh:mm a"
	const val API_TIME_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'"
	const val DAY_IN_MS = 86400000

	// Page sizes for different modules

	const val PAGE_SIZE_READ_LATER = 6
	const val PAGE_SIZE_CATEGORIES = 6
	const val PAGE_SIZE_TOP_NEWS = 8
	const val PAGE_SIZE_SEARCH = 6
	const val PAGE_SIZE_ITEMS_WITHIN_CATEGORY = 10
}