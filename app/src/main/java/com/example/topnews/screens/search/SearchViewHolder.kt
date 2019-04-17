package com.example.topnews.screens.search

import android.view.View
import base.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.data.model.Article
import kotlinx.android.synthetic.main.item_search_result.view.ivImg
import kotlinx.android.synthetic.main.item_search_result.view.tvPublishTime
import kotlinx.android.synthetic.main.item_search_result.view.tvSource
import kotlinx.android.synthetic.main.item_search_result.view.tvTitle

class SearchViewHolder(itemView: View) : BaseViewHolder<Article>(itemView) {

	override fun bind(dataItem: Article) {
		itemView.apply {
			tvTitle.text = dataItem.title
			tvSource.text = dataItem.source.getValue("name")
			tvPublishTime.text = dataItem.publishedAt
		}
		Glide.with(itemView.context).load(dataItem.urlToImage).apply(RequestOptions().override(400, 600))
			.into(itemView.ivImg)
	}
}