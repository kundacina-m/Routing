package com.example.topnews.screens.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import base.BasePagedListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.utils.asString
import kotlinx.android.synthetic.main.item_vertical_article.view.ivImg
import kotlinx.android.synthetic.main.item_vertical_article.view.tvPublishTime
import kotlinx.android.synthetic.main.item_vertical_article.view.tvSource
import kotlinx.android.synthetic.main.item_vertical_article.view.tvTitle

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
										 BasePagedListAdapter.ViewHolderAdapterBinder<Article> {

	override fun bind(dataItem: Article) {

		itemView.apply {
			tvTitle.text = dataItem.title
			tvSource.text = dataItem.source
			tvPublishTime.text = dataItem.publishedAt?.asString(context!!)
		}
		Glide.with(itemView.context).load(dataItem.urlToImage).apply(getGlideOptions())
			.into(itemView.ivImg)
	}

	private fun getGlideOptions(): RequestOptions {
		return RequestOptions()
			.centerCrop()
			.placeholder(R.drawable.loading)
			.error(R.drawable.ic_error_image)
			.diskCacheStrategy(DiskCacheStrategy.ALL)
			.priority(Priority.HIGH)
	}
}