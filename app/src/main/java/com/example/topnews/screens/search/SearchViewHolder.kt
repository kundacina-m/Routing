package com.example.topnews.screens.search

import android.view.View
import base.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.data.db.Article
import com.example.topnews.utils.Constants.DATE_ONLY
import com.example.topnews.utils.asString
import kotlinx.android.synthetic.main.item_vertical_article.view.*

class SearchViewHolder(itemView: View) : BaseViewHolder<Article>(itemView) {

	override fun bind(dataItem: Article) {
		itemView.apply {
			tvTitle.text = dataItem.title
			tvSource.text = dataItem.source
			tvPublishTime.text = dataItem.publishedAt.toString()
		}
		Glide.with(itemView.context).load(dataItem.urlToImage).apply(RequestOptions().override(400, 600))
			.into(itemView.ivImg)
	}
}