package com.example.topnews.screens.articlescategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BasePagedListAdapter
import com.example.topnews.R.layout
import com.example.topnews.data.db.Article
import com.example.topnews.screens.readlater.ReadLaterAdapter

class ArticlesCategoryAdapter : BasePagedListAdapter<Article>(ReadLaterAdapter.articleDiffCallback) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		ArticlesCategoryViewHolder(
			LayoutInflater.from(parent.context).inflate(
				layout.item_vertical_article,
				parent,
				false
			)
		)
}