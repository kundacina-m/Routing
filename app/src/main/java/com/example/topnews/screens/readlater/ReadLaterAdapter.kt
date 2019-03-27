package com.example.topnews.screens.readlater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.Article
import kotlin.properties.Delegates

class ReadLaterAdapter : BaseAdapter<Article>(), ReadLaterViewHolder.ArticleCheckbox {

    var observable = ReadLaterObservable()
    var checkedArticles: ArrayList<Article> = arrayListOf()
    var handleMenu: ((Boolean) -> Unit?)? = null

    var selectionInProgress: Boolean by Delegates.observable(false) { _, _, newValue ->
        handleMenu?.invoke(newValue)
        observable.notifyAll(null, null, newValue)
    }

    var checkAll: Boolean by Delegates.observable(false) { _, _, newValue ->
        observable.notifyAll(null, newValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ReadLaterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_read_later,
                parent,
                false
            )
        ).apply { onChecked = this@ReadLaterAdapter::onChecked }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        observable.addObserver(holder as ReadLaterViewHolder)

        holder.itemView.apply {
            setOnClickListener {
                selectionInProgress = false
                oneClickListener?.invoke(getItemOnPosition(holder.adapterPosition))
            }
            setOnLongClickListener { setupLongClickListenerAction(holder.adapterPosition);true }
        }
    }

    private fun setupLongClickListenerAction(position: Int) {
        if (!selectionInProgress) {
            selectionInProgress = true
            observable.notifyAll(getItemOnPosition(position), null, selectionInProgress)
        }
    }

    override fun onChecked(article: Article, check: Boolean) {
        when {
            check -> {
                if (!checkedArticles.contains(article)) checkedArticles.add(article)
            }
            !check -> {
                checkedArticles.remove(article)
            }
        }
    }

    interface PopUpMenu {
        fun showMenu(visibility: Boolean)
    }
}