package com.example.topnews.domain.crud

import com.example.topnews.domain.WrappedResponse
import io.reactivex.Single

interface RemoteCRUD<T> {
	fun add(item: T): Single<WrappedResponse<Boolean>>
	fun remove(item: T): Single<WrappedResponse<Boolean>>
	fun get(id: String): Single<WrappedResponse<T>>
	fun getItemsByQuery(queryParams: Map<String, String>): Single<WrappedResponse<List<T>>>
}