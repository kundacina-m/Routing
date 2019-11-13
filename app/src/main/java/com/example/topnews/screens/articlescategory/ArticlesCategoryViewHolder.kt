package com.example.topnews.screens.articlescategory

import android.view.View
import base.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.data.Article
import com.example.topnews.utils.asString
import kotlinx.android.synthetic.main.item_vertical_article.view.ivImg
import kotlinx.android.synthetic.main.item_vertical_article.view.tvPublishTime
import kotlinx.android.synthetic.main.item_vertical_article.view.tvSource
import kotlinx.android.synthetic.main.item_vertical_article.view.tvTitle

class ArticlesCategoryViewHolder(itemView: View) : BaseViewHolder<Article>(itemView) {

	override fun bind(dataItem: Article) {
		populateViewWithData(dataItem)
	}

	private fun populateViewWithData(dataItem: Article) =
		itemView.apply {
			tvTitle.text = dataItem.title
			tvSource.text = dataItem.source
			tvPublishTime.text = dataItem.publishedAt?.asString(context!!)

			Glide.with(this.context)
				.load(dataItem.urlToImage)
				.apply(getGlideOptions())
				.into(ivImg)
		}

	private fun getGlideOptions() =
		RequestOptions()
			.centerCrop()
			.error(R.drawable.ic_error_image)
			.diskCacheStrategy(DiskCacheStrategy.ALL)
			.priority(Priority.HIGH)

}