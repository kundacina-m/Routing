package com.example.topnews.domain.crud

import com.example.topnews.domain.WrappedResponse
import io.reactivex.Flowable

interface LocalCRUD<T> {
	fun add(item: T): Flowable<WrappedResponse<Boolean>>
	fun remove(item: T): Flowable<WrappedResponse<Boolean>>
	fun get(id: String): Flowable<WrappedResponse<T>>
	fun getAll(): Flowable<WrappedResponse<ArrayList<T>>>
}