package com.example.topnews.screens.topnews

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import base.BasePagedListAdapter
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.utils.Constants.articleDiffCallback
import kotlinx.android.synthetic.main.item_top_news.view.ivArticleImage
import kotlinx.android.synthetic.main.item_top_news.view.tvArticleTitle

class TopNewsAdapter : BasePagedListAdapter<Article>(articleDiffCallback) {

	var onClickWithTransition: ((Article, ImageView, TextView) -> Unit?)? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		TopNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_news, parent, false))

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		transitionSetup(holder)
		super.onBindViewHolder(holder, position)

		clickListener(holder)

	}

	private fun clickListener(holder: ViewHolder) {

		holder.itemView.setOnClickListener {

			onClickWithTransition?.invoke(
				getItem(holder.adapterPosition)!!, it.ivArticleImage,
				it.tvArticleTitle
			)

		}
	}

	private fun transitionSetup(holder: ViewHolder) {

		ViewCompat.setTransitionName(
			holder.itemView.ivArticleImage,
			getItem(holder.adapterPosition)?.publishedAt.toString()
		)

		ViewCompat.setTransitionName(
			holder.itemView.tvArticleTitle,
			(getItem(holder.adapterPosition)?.publishedAt.toString() + "title")
		)
	}

}