package com.example.topnews.screens.readlater

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.Article

class ReadLaterAdapter: BaseAdapter<Article>() {

    override fun getItemViewId(): Int = R.layout.item_read_later

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = ReadLaterViewHolder(view)

}