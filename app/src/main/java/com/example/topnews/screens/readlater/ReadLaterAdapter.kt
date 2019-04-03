package com.example.topnews.screens.readlater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.ImageDialog
import com.example.topnews.R
import com.example.topnews.screens.Article
import kotlinx.android.synthetic.main.item_vertical_article.view.ivImg
import kotlin.properties.Delegates

class ReadLaterAdapter : BaseAdapter<Article>(), ReadLaterViewHolder.ArticleCheckbox {

	private var observable = ReadLaterObservable()
	var checkedArticles: ArrayList<Article> = arrayListOf()
	var handleMenu: ((Boolean, Boolean) -> Unit?)? = null

	var selectionInProgress: Boolean by Delegates.observable(false) { _, _, newValue ->
		handleMenu?.invoke(newValue, newValue)
		observable.notifyAll(checkedArticles, null, newValue)
	}

	var checkAll: Boolean by Delegates.observable(false) { _, _, newValue ->
		observable.notifyAll(checkedArticles, newValue)
		if (checkAll) uncheckedArticles = arrayListOf()
	}

	var uncheckedArticles: ArrayList<Article> = arrayListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		ReadLaterViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.item_vertical_article,
				parent,
				false
			)
		).apply { onChecked = this@ReadLaterAdapter::onChecked }

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		super.onBindViewHolder(holder, position)

		observable.addObserver(holder as ReadLaterViewHolder)

		val item = getItemOnPosition(holder.adapterPosition)
		holder.itemView.apply {
			setOnClickListener {
				selectionInProgress = false
				oneClickListener?.invoke(item)
			}
			setOnLongClickListener { setupLongClickListenerAction(holder.adapterPosition);true }
			ivImg.setOnClickListener {
				ImageDialog.build(context) {
					urlToImg = item.urlToImage
					themeId = R.style.AppTheme_Dialog_NoClick
				}.show()
			}
		}
	}

	private fun setupLongClickListenerAction(position: Int) {
		if (!selectionInProgress) {
			selectionInProgress = true
			observable.notifyAll(arrayListOf(getItemOnPosition(position)), null, selectionInProgress)
		}
	}

	override fun onChecked(article: Article, check: Boolean) {
		when {
			check -> {
				if (!checkedArticles.contains(article)) checkedArticles.add(article)
				uncheckedArticles.remove(article)
			}
			!check -> {
				handleMenu?.invoke(checkedArticles.isNotEmpty(), true)
				checkedArticles.remove(article)
				if (!uncheckedArticles.contains(article)) uncheckedArticles.add(article)
			}
		}
	}

	interface PopUpMenu {
		fun showMenu(visibilityRemove: Boolean, visibilitySelectAll: Boolean)
	}
}