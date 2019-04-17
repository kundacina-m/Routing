package com.example.topnews.screens.topnews

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.data.model.Article
import kotlinx.android.synthetic.main.item_top_news.view.ivArticleImage
import kotlinx.android.synthetic.main.item_top_news.view.tvArticleTitle

class TopNewsAdapter : BaseAdapter<Article>() {

	var onClickWithTransition: ((Article, ImageView, TextView) -> Unit?)? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		TopNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_news, parent, false))

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

		ViewCompat.setTransitionName(holder.itemView.ivArticleImage,
			getItemOnPosition(holder.adapterPosition).publishedAt)
		ViewCompat.setTransitionName(holder.itemView.tvArticleTitle,
			(getItemOnPosition(holder.adapterPosition).publishedAt + "title"))

		super.onBindViewHolder(holder, position)

		holder.itemView.setOnClickListener {
			onClickWithTransition?.invoke(getItemOnPosition(holder.adapterPosition), it.ivArticleImage,
				it.tvArticleTitle)
			//            oneClickListener?.invoke(getItemOnPosition(position))
		}
	}

	interface onClickTransition {
		fun onItemClickWithImgTransition(dataItem: Article, img: ImageView, title: TextView)
	}
}