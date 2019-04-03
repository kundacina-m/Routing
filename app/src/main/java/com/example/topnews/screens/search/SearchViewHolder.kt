package com.example.topnews.screens.search

import android.view.View
import base.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.screens.Article
import kotlinx.android.synthetic.main.item_vertical_article.view.ivImg
import kotlinx.android.synthetic.main.item_vertical_article.view.tvPublishTime
import kotlinx.android.synthetic.main.item_vertical_article.view.tvSource
import kotlinx.android.synthetic.main.item_vertical_article.view.tvTitle

class SearchViewHolder(itemView: View) : BaseViewHolder<Article>(itemView) {

	override fun bind(dataItem: Article) {
		itemView.apply {
			tvTitle.text = dataItem.title
			tvSource.text = dataItem.source.getValue("name")
			tvPublishTime.text = dataItem.publishedAt
		}

		val options = RequestOptions()
			.centerCrop()
			.placeholder(R.drawable.loading)
			.error(R.drawable.error_img)
			.diskCacheStrategy(DiskCacheStrategy.ALL)
			.priority(Priority.HIGH)

		Glide.with(itemView.context).load(dataItem.urlToImage).apply(options)
			.into(itemView.ivImg)
	}
}