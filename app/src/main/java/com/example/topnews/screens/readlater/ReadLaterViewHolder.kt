package com.example.topnews.screens.readlater

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.screens.Article
import base.BaseViewHolder
import com.example.topnews.screens.Constants
import kotlinx.android.synthetic.main.item_read_later.view.*

class ReadLaterViewHolder(itemView: View) : BaseViewHolder<Article>(itemView) {

    override fun bind(dataItem: Article) {
        itemView.apply {
            tvTitleReadLater.text = dataItem.title
            tvSourceLaterRead.text = dataItem.source.getValue(Constants.MAP_SOURCE_KEY_NAME)
            tvPublishTimeReadLater.text = dataItem.publishedAt
        }
        Glide.with(itemView.context).load(dataItem.urlToImage).apply(RequestOptions().override(400, 600))
            .into(itemView.ivImgReadLater)
    }
}