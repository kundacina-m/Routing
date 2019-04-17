package com.example.topnews.domain.crud

import com.example.topnews.domain.OnWrappedResponse

interface RemoteCRUD<T> {
	fun addRemote(item: T, onWrappedResponse: OnWrappedResponse<Boolean>)
	fun removeRemote(item: T, onWrappedResponse: OnWrappedResponse<Boolean>)
	fun getRemote(id: String, onWrappedResponse: OnWrappedResponse<T>)
	fun getAllRemote(onWrappedResponse: OnWrappedResponse<List<T>>)
}