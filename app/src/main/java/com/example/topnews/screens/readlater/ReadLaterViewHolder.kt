package com.example.topnews.screens.readlater

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.screens.Article
import base.BaseViewHolder
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.topnews.R
import com.example.topnews.utils.Constants
import com.example.topnews.utils.ObservableData
import kotlinx.android.synthetic.main.item_read_later.view.*
import java.util.*


class ReadLaterViewHolder(itemView: View) : BaseViewHolder<Article>(itemView), ReadLaterObserver {

    lateinit var article: Article

    override fun updateView(o: Observable, observedData: ObservableData) {

        observedData.apply {

            checkAll?.let { itemView.cbToSelect.isChecked = it }
            articles?.let { itemView.cbToSelect.isChecked = it.contains(article) }
            showCheckbox?.let { itemView.cbToSelect.visibility = if (it) View.VISIBLE else View.GONE }
        }
    }

    var onChecked: ((Article, Boolean) -> Unit?)? = null


    override fun bind(dataItem: Article) {
        article = dataItem
        itemView.apply {
            tvTitleReadLater.text = dataItem.title
            tvSourceLaterRead.text = dataItem.source.getValue(Constants.MAP_SOURCE_KEY_NAME)
            tvPublishTimeReadLater.text = dataItem.publishedAt
        }

        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error_img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(itemView.context).load(dataItem.urlToImage).apply(options)
            .into(itemView.ivImgReadLater)

        setCheckListener(dataItem)
    }

    private fun setCheckListener(article: Article) {
        itemView.cbToSelect.setOnCheckedChangeListener { _, isChecked ->
            onChecked?.invoke(article, isChecked)
        }
    }

    interface ArticleCheckbox {
        fun onChecked(article: Article, check: Boolean)
    }
}