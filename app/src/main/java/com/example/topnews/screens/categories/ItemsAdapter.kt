package com.example.topnews.screens.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.data.db.Article

class ItemsAdapter : BaseAdapter<Article>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		ItemsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_article, parent, false))

}
