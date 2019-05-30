package com.example.topnews.screens.topnews

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import base.BasePagedListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.data.db.Article
import kotlinx.android.synthetic.main.item_top_news.view.ivArticleImage
import kotlinx.android.synthetic.main.item_top_news.view.tvArticleTitle

class TopNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
										  BasePagedListAdapter.ViewHolderAdapterBinder<Article> {

	override fun bind(dataItem: Article) {

		itemView.tvArticleTitle.text = dataItem.title
		loadImage(dataItem)

	}

	private fun loadImage(dataItem: Article) {

		val options = RequestOptions()
			.centerCrop()
			.placeholder(R.drawable.loading)
			.error(R.drawable.ic_error_image)
			.diskCacheStrategy(DiskCacheStrategy.ALL)
			.priority(Priority.HIGH)

		Glide.with(itemView.context).load(dataItem.urlToImage).apply(options)
			.into(itemView.ivArticleImage)
	}
}