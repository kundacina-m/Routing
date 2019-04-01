package com.example.topnews.screens.topnews

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.screens.Article
import base.BaseViewHolder
import kotlinx.android.synthetic.main.item_top_news.view.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.Priority
import com.example.topnews.R


class TopNewsViewHolder(itemView: View) : BaseViewHolder<Article>(itemView){

    override fun bind(dataItem: Article) {
        itemView.tvArticleTitle.text = dataItem.title

        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error_img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(itemView.context).load(dataItem.urlToImage).apply(options)
            .into(itemView.ivArticleImage)
    }
}