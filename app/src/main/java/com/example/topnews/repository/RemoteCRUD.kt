package com.example.topnews.repository

interface RemoteCRUD<T> {
    fun addRemote(item: T)
    fun removeRemote(item: T, func: (Boolean) -> Unit)
    fun getRemote(id: String,func: (T) -> Unit)
    fun getAllRemote(func: (ArrayList<T>) -> Unit)
}