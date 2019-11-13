package com.example.topnews.screens.articlescategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import base.BaseAdapter
import com.example.topnews.R.layout
import com.example.topnews.data.Article

class ArticlesCategoryAdapter : BaseAdapter<Article>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		ArticlesCategoryViewHolder(
			LayoutInflater.from(parent.context).inflate(
				layout.item_vertical_article,
				parent,
				false
			)
		)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		super.onBindViewHolder(holder, position)
		setupListeners(holder)
	}

	private fun setupListeners(holder: ViewHolder) {
		holder.itemView.setOnClickListener {
			clickListener?.invoke(getItemOnPosition(holder.adapterPosition))
		}
	}
}