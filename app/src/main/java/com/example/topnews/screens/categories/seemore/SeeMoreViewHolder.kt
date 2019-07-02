package com.example.topnews.screens.categories.seemore

import android.view.View
import base.BaseViewHolder
import com.example.topnews.data.db.Article

class SeeMoreViewHolder(itemView: View) : BaseViewHolder<Article>(itemView) {

	override fun bind(dataItem: Article) {

		itemView.setOnClickListener {
			CategoryModule.categoryClick.post(dataItem.url)
		}
	}
}