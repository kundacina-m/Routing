package com.example.topnews.screens


data class Article(
    val source: HashMap<String, String>,
    val author: String,
    val title: String,
    val description: String,
    val urlToArticle: String,
    val imageUrl: String,
    val publishedAt: String,
    val content: String
)