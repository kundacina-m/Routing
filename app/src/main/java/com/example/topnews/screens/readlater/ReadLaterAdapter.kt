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
import kotlin.properties.Delegates

class ReadLaterAdapter(private val viewModel: ReadLaterViewModel) :
	BasePagedListAdapter<Article>(articleDiffCallback),
	ReadLaterViewHolder.ArticleCheckbox {

	private var observable = ReadLaterObservable()
	var checkedArticles: ArrayList<Article> = arrayListOf()
	var handleMenu: ((Boolean) -> Unit?)? = null

	var selectionInProgress: Boolean by Delegates.observable(false) { _, _, isSelection ->
		handleMenu?.invoke(isSelection)
		observable.notifyAll(checkedArticles, isSelection)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		ReadLaterViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.item_vertical_article, parent, false
			)
		).apply {
			onChecked = this@ReadLaterAdapter::onChecked
			observable.addObserver(this)
		}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		super.onBindViewHolder(holder, position)


		(holder as ReadLaterViewHolder).onRecycledView(checkedArticles.contains(getItem(holder.adapterPosition)))
		setupListeners(holder)
	}

	private fun setupListeners(holder: ViewHolder) {

		holder.itemView.ivImg.setOnLongClickListener {
			addTag(it.context)
			true
		}

		holder.itemView.setOnLongClickListener {
			if (!selectionInProgress) {
				selectionInProgress = true
				observable.notifyAll(arrayListOf(getItem(holder.adapterPosition)!!), selectionInProgress)
			}
			true
		}

		holder.itemView.setOnClickListener {
			clickListener?.invoke(getItem(holder.adapterPosition)!!)
		}

	}

	private fun addTag(context: Context) {

		TagDialog.build(context) {
			//			article = getItemOnPosition(holder.adapterPosition)
			confirmedTag = viewModel::addTagToArticle
		}.show()
	}

	override fun onChecked(article: Article, check: Boolean) {
		when {
			check -> {
				if (!checkedArticles.contains(article)) checkedArticles.add(article)
			}
			!check -> {
				checkedArticles.remove(article)
				if (checkedArticles.isNotEmpty())
					handleMenu?.invoke(true)
				else selectionInProgress = false

			}
		}
	}

	interface PopUpMenu {
		fun showMenu(visibilityRemove: Boolean)
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