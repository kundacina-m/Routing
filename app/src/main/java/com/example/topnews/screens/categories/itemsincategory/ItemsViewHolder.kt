package com.example.topnews.screens.categories.itemsincategory

import android.view.View
import base.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.data.db.Article
import kotlinx.android.synthetic.main.item_horizontal_article.view.ivImg
import kotlinx.android.synthetic.main.item_horizontal_article.view.tvTitle

class ItemsViewHolder(itemView: View) : BaseViewHolder<Article>(itemView) {

	override fun bind(dataItem: Article) {
		loadImage(dataItem)
		itemView.tvTitle.text = dataItem.title

		itemView.setOnClickListener {
			ArticleClickedEventBus.post(dataItem)
		}
	}

	private fun loadImage(dataItem: Article) {

		val options = RequestOptions()
			.centerCrop()
			.placeholder(R.drawable.loading)
			.error(R.drawable.ic_error_image)
			.diskCacheStrategy(DiskCacheStrategy.ALL)
			.priority(Priority.HIGH)

		Glide.with(itemView.context).load(dataItem.urlToImage).apply(options)
			.into(itemView.ivImg)
	}
}