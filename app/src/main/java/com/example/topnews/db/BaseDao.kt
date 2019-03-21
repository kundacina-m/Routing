package com.example.topnews.db

interface BaseDao<T> {
    fun insertItem(item: T): Boolean
    fun removeItem(item: T): Boolean
    fun updateItem(item: T): Boolean
    fun readAll(): ArrayList<T>
}