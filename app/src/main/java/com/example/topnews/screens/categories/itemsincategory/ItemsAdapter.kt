package com.example.topnews.screens.categories.itemsincategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import base.BaseAdapter
import com.example.topnews.R.layout
import com.example.topnews.data.db.Article
import com.example.topnews.screens.categories.seemore.SeeMoreViewHolder

class ItemsAdapter : BaseAdapter<Article>() {

	override fun getItemViewType(position: Int): Int {
		return if (position == 10) 1 else 0
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return when (viewType) {
			0 -> ItemsViewHolder(
				LayoutInflater.from(parent.context).inflate(
					layout.item_horizontal_article,
					parent,
					false
				)
			)
			else -> SeeMoreViewHolder(
				LayoutInflater.from(parent.context).inflate(
					layout.item_see_more,
					parent,
					false
				)
			)
		}

	}

	fun submitData(list: List<Article>, category: String) {
		setData(arrayListOf<Article>().apply {
			addAll(list)
			add(
				Article(
					null,
					null,
					null,
					null,
					category,
					null,
					null,
					null
				)
			)
		})

	}
}
