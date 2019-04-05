package com.example.topnews.domain.crud

import com.example.topnews.domain.OnWrappedResponse

interface LocalCRUD<T> {
	fun addLocal(item: T, onWrappedResponse: OnWrappedResponse<Boolean>)
	fun removeLocal(item: T, onWrappedResponse: OnWrappedResponse<Boolean>)
	fun getLocal(id: String, onWrappedResponse: OnWrappedResponse<T>)
	fun getAllLocal(onWrappedResponse: OnWrappedResponse<List<T>>)
}