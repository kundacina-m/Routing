package com.example.topnews.screens.articlescategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.Article
import com.example.topnews.screens.readlater.ReadLaterViewHolder

class ArticlesCategoryAdapter: BaseAdapter<Article>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ReadLaterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_read_later, parent, false))
}