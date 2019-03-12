package com.example.topnews.screens.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.screens.Article
import com.example.topnews.screens.OnRVItemClickListener
import com.example.topnews.screens.Utils.WrappedAdapter
import kotlinx.android.synthetic.main.item_search_result.view.*

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    WrappedAdapter.ViewHolderAdapterBinder<Article> {
    override fun bind(dataItem: Article, listener: OnRVItemClickListener<Article>?) {
        itemView.tvTitle.text = dataItem.title
        itemView.tvSource.text = dataItem.source.getValue("name")
        itemView.tvPublishTime.text = dataItem.publishedAt
        Glide.with(itemView.context).load(dataItem.imageUrl).apply(RequestOptions().override(400, 600))
            .into(itemView.ivImg)


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