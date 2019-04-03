package com.example.topnews.repository

interface LocalCRUD<T> {
	fun addLocal(item: T, func: (Boolean) -> Unit)
	fun removeLocal(item: T, func: (Boolean) -> Unit)
	fun getLocal(id: String, func: (T) -> Unit)
	fun getAllLocal(func: (ArrayList<T>) -> Unit)
}