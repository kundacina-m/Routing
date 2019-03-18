package com.example.topnews.screens

interface OnRVItemClickListener<in T> {
    fun itemClicked(dataItem: T)
}