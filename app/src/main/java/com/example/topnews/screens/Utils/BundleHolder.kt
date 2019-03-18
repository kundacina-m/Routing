package com.example.topnews.screens.Utils

import android.os.Bundle
import com.example.topnews.screens.Article
import com.example.topnews.screens.Constants

object BundleHolder {
    fun getBundleForDetails(dataItem: Article): Bundle {
        val bundle = Bundle()
        bundle.apply {
            putString(Constants.ARG_IMG_URL_BUNDLE, dataItem.imageUrl)
            putString(Constants.ARG_SOURCE_BUNDLE, dataItem.source[Constants.MAP_SOURCE_KEY_NAME])
            putString(Constants.ARG_TITLE_BUNDLE, dataItem.title)
            putString(Constants.ARG_DESCRIPTION_BUNDLE, dataItem.description)
            putString(Constants.ARG_CONTENT_BUNDLE, dataItem.content)
            putString(Constants.ARG_PUBLISHED_AT_BUNDLE, dataItem.publishedAt)
            putString(Constants.ARG_AUTHOR_BUNDLE, dataItem.author)
            putString(Constants.ARG_URL_WEB_BUNDLE, dataItem.urlToArticle)
        }
        return bundle
    }
}