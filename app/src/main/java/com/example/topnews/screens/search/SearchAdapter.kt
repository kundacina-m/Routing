package com.example.topnews.screens.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.Article

class SearchAdapter: BaseAdapter<Article>() {

    override fun getItemViewId(): Int = R.layout.item_search_result

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = SearchViewHolder(view)

}