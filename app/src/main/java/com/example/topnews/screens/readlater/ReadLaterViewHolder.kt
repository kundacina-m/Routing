package com.example.topnews.screens.readlater

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.screens.Article
import com.example.topnews.screens.OnRVItemClickListener
import com.example.topnews.screens.Utils.WrappedAdapter
import kotlinx.android.synthetic.main.item_read_later.view.*

class ReadLaterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    WrappedAdapter.ViewHolderAdapterBinder<Article> {
    override fun bind(dataItem: Article, listener: OnRVItemClickListener<Article>?) {
        itemView.tvTitleReadLater.text = dataItem.title
        itemView.tvSourceLaterRead.text = dataItem.source.getValue("name")
        itemView.tvPublishTimeReadLater.text = dataItem.publishedAt
        Glide.with(itemView.context).load(dataItem.imageUrl).apply(RequestOptions().override(400, 600))
            .into(itemView.ivImgReadLater)

        listener?.let {
            setupClickListeners(dataItem, listener)
        }
    }

    private fun setupClickListeners(dataItem: Article, listener: OnRVItemClickListener<Article>) {
        itemView.setOnClickListener {
            listener.itemClicked(dataItem)
        }
    }


}