package com.example.topnews.screens.readlater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.screens.Article

class ReadLaterAdapter : BaseAdapter<Article>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ReadLaterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_read_later, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnClickListener {
            oneClickListener?.invoke(getItemOnPosition(position))
        }
    }
}