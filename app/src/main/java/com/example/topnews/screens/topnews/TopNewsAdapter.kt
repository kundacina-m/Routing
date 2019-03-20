package com.example.topnews.screens.topnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.Article
import kotlinx.android.synthetic.main.item_top_news.view.*

class TopNewsAdapter : BaseAdapter<Article>() {

    var onStarClickListener: ((Article) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        TopNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_news, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnClickListener {
            oneClickListener?.invoke(getItemOnPosition(position))
        }

        holder.itemView.btReadLater.setOnClickListener {
            onStarClickListener?.invoke(getItemOnPosition(position))
        }
    }

    interface onFavouriteClickListener{
        fun onStarClick(article: Article)
    }
}