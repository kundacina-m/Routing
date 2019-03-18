package com.example.topnews.screens

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.topnews.R
import com.example.topnews.screens.readlater.ReadLaterViewHolder
import com.example.topnews.screens.search.SearchViewHolder
import com.example.topnews.screens.topnews.TopNewsViewHolder

object ViewHolderFactory {

    fun getRightViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_read_later -> ReadLaterViewHolder(view)
            R.layout.item_search_result -> SearchViewHolder(view)
            R.layout.item_top_news -> TopNewsViewHolder(view)
            else -> {
                TopNewsViewHolder(view)
            }
        }
    }

}