package com.example.topnews.screens.topnews

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.screens.Article
import com.example.topnews.screens.OnRVItemClickListener
import com.example.topnews.screens.WrappedAdapter
import kotlinx.android.synthetic.main.item_top_news.view.*

class TopNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    WrappedAdapter.ViewHolderAdapterBinder<Article> {
    override fun bind(dataItem: Article, listener: OnRVItemClickListener<Article>?) {
        itemView.tvArticleTitle.text = dataItem.title
        Glide.with(itemView.context).load(dataItem.imageUrl).apply(RequestOptions().override(400, 600))
            .into(itemView.ivArticleImage)

        listener?.let {
            setupClickListeners(dataItem, listener)
        }
    }

    private fun setupClickListeners(dataItem: Article, listener: OnRVItemClickListener<Article>) {
        itemView.btReadLater.setOnClickListener {
        }

        itemView.setOnClickListener {
            listener.itemClicked(dataItem)
        }
    }
}