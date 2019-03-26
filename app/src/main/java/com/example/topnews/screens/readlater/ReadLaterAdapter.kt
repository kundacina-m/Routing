package com.example.topnews.screens.readlater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.Article
import kotlinx.android.synthetic.main.item_read_later.view.*
import kotlin.properties.Delegates

class ReadLaterAdapter : BaseAdapter<Article>(), ReadLaterViewHolder.ArticleCheckbox {

    var handleMenu: ((Boolean) -> Unit?)? = null

    var checkedArticles: ArrayList<Article> by Delegates.observable(arrayListOf()) { property, oldValue, newValue ->
        handleMenu?.invoke(false)
        if (!holders.isEmpty()) {
            holders.forEach {
                it.itemView.cbToSelect.visibility = View.GONE
            }
        }
    }
    private var holders: ArrayList<ReadLaterViewHolder> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ReadLaterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_read_later,
                parent,
                false
            )
        ).apply {
            onChecked = this@ReadLaterAdapter::checked
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        checkedArticles = arrayListOf()

        holder.itemView.setOnClickListener {
            oneClickListener?.invoke(getItemOnPosition(position))
        }

        holder.itemView.setOnLongClickListener {
            holder.itemView.cbToSelect.isChecked = true
            handleMenu?.invoke(true)
            holders.forEach {
                it.itemView.cbToSelect.visibility = View.VISIBLE
            }
            true
        }

        holders.add(holder as ReadLaterViewHolder)
        dataSize = getData().size
    }

    fun checkAll() {
        val isThatAll = checkedArticles.size == dataSize
        holders.forEach {
            it.itemView.cbToSelect.isChecked = !isThatAll
        }

    }

    fun getChecked(): ArrayList<Article> {
        return checkedArticles
    }

    fun updateDataSize(){
        dataSize = getData().size
    }


    interface PopUpMenu {
        fun showMenu(visibility: Boolean)
    }

    override fun checked(article: Article, check: Boolean) {
        when {
            check -> {
                if (!checkedArticles.contains(article))
                    checkedArticles.add(article)
            }
            !check -> {
                checkedArticles.remove(article)
                if (checkedArticles.isEmpty()) {
                    checkedArticles = arrayListOf()
                }
            }
        }
    }

}