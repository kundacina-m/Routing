package com.example.topnews.screens.articlescategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import base.BasePagedListAdapter
import com.example.topnews.R.layout
import com.example.topnews.data.db.Article
import com.example.topnews.screens.readlater.ReadLaterAdapter

class ArticlesCategoryAdapter : BasePagedListAdapter<Article>(ReadLaterAdapter.articleDiffCallback) {
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
			oneClickListener?.invoke(getItem(holder.adapterPosition)!!)
		}
	}
}