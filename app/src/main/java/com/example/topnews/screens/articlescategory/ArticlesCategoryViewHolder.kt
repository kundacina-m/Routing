package com.example.topnews.screens.articlescategory

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.screens.ImageDialog
import com.example.topnews.utils.asString
import kotlinx.android.synthetic.main.item_vertical_article.view.ivImg
import kotlinx.android.synthetic.main.item_vertical_article.view.tvPublishTime
import kotlinx.android.synthetic.main.item_vertical_article.view.tvSource
import kotlinx.android.synthetic.main.item_vertical_article.view.tvTitle

class ArticlesCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
												   BaseAdapter.ViewHolderAdapterBinder<Article> {

	lateinit var article: Article

	override fun bind(dataItem: Article) {

		article = dataItem

		populateViewWithData(dataItem)

		setOnImgClickListener(dataItem)

	}

	private fun populateViewWithData(dataItem: Article) {
		itemView.apply {
			tvTitle.text = dataItem.title
			tvSource.text = dataItem.source
			tvPublishTime.text = dataItem.publishedAt?.asString()
		}

		Glide.with(itemView.context).load(dataItem.urlToImage).apply(getGlideOptions())
			.into(itemView.ivImg)
	}

	private fun getGlideOptions(): RequestOptions {
		return RequestOptions()
			.centerCrop()
			.placeholder(R.drawable.loading)
			.error(R.drawable.error_img)
			.diskCacheStrategy(DiskCacheStrategy.ALL)
			.priority(Priority.HIGH)
	}

	private fun setOnImgClickListener(dataItem: Article) {
		itemView.ivImg.setOnClickListener {
			ImageDialog.build(itemView.context) {
				urlToImg = dataItem.urlToImage
				themeId = R.style.AppTheme_Dialog_NoClick
			}.show()
		}
	}

}