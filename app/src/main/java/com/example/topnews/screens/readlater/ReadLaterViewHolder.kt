package com.example.topnews.screens.readlater

import android.view.View
import base.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.screens.ImageDialog
import com.example.topnews.utils.Constants.DATE_ONLY
import com.example.topnews.utils.ObservableData
import com.example.topnews.utils.asString
import kotlinx.android.synthetic.main.item_vertical_article.view.cbToSelect
import kotlinx.android.synthetic.main.item_vertical_article.view.ivImg
import kotlinx.android.synthetic.main.item_vertical_article.view.tvPublishTime
import kotlinx.android.synthetic.main.item_vertical_article.view.tvSource
import kotlinx.android.synthetic.main.item_vertical_article.view.tvTitle
import java.util.Observable

class ReadLaterViewHolder(itemView: View) : BaseViewHolder<Article>(itemView), ReadLaterObserver {

	lateinit var article: Article

	override fun updateView(o: Observable, observedData: ObservableData) {

		observedData.apply {

			checkAll?.let { itemView.cbToSelect.isChecked = it }
			articles?.let { itemView.cbToSelect.isChecked = it.contains(article) }
			showCheckbox?.let { itemView.cbToSelect.visibility = if (it) View.VISIBLE else View.GONE }
		}
	}

	var onChecked: ((Article, Boolean) -> Unit?)? = null

	override fun bind(dataItem: Article) {

		article = dataItem

		populateViewWithData(dataItem)

		setCheckListener(dataItem)

		setOnImgClickListener(dataItem)

	}

	private fun populateViewWithData(dataItem: Article) {
		itemView.apply {
			tvTitle.text = dataItem.title
			tvSource.text = dataItem.source
			tvPublishTime.text = dataItem.publishedAt.asString(DATE_ONLY)
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

	private fun setCheckListener(article: Article) {
		itemView.cbToSelect.setOnCheckedChangeListener { _, isChecked ->
			onChecked?.invoke(article, isChecked)
		}
	}

	interface ArticleCheckbox {
		fun onChecked(article: Article, check: Boolean)
	}
}