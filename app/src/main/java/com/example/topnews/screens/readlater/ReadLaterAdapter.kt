package com.example.topnews.screens.readlater

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import base.BasePagedListAdapter
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.screens.TagDialog
import kotlinx.android.synthetic.main.item_vertical_article.view.ivImg

class ReadLaterAdapter(private val viewModel: ReadLaterViewModel) :
	BasePagedListAdapter<Article>(articleDiffCallback) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		ReadLaterViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.item_vertical_article,
				parent,
				false
			)
		)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		super.onBindViewHolder(holder, position)
		setupListeners(holder)
	}

	private fun setupListeners(holder: ViewHolder) {

		holder.itemView.ivImg.setOnLongClickListener {
			addTag(it.context)
			true
		}

		holder.itemView.setOnClickListener {
			oneClickListener?.invoke(getItem(holder.adapterPosition)!!)
		}

	}

	private fun addTag(context: Context) {

		TagDialog.build(context) {
			//			article = getItemOnPosition(holder.adapterPosition)
			confirmedTag = viewModel::addTagToArticle
		}.show()
	}

	companion object {

		val articleDiffCallback = object : DiffUtil.ItemCallback<Article>() {
			override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
				oldItem.url == newItem.url

			override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
				oldItem == newItem
		}
	}
}