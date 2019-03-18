package com.example.topnews.screens.topnews

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.Article

class TopNewsAdapter : BaseAdapter<Article>() {

    override fun getItemViewId(): Int = R.layout.item_top_news

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = TopNewsViewHolder(view)

}