package com.example.topnews.utils

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.topnews.domain.WrappedResponse.OnError

data class WrapperPagination<T> (
	val pagedList: LiveData<PagedList<T>>,
	val error: LiveData<OnError<Nothing>>? = null
)