package com.example.topnews.screens.articlescategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R.layout
import com.example.topnews.data.db.Article

class ArticlesCategoryAdapter : BaseAdapter<Article>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		ArticlesCategoryViewHolder(
			LayoutInflater.from(parent.context).inflate(
				layout.item_vertical_article,
				parent,
				false
			)
		)
}