package com.example.topnews.screens.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R

class CategoriesAdapter : BaseAdapter<String>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CategoriesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnClickListener {
            oneClickListener?.invoke(getItemOnPosition(position))
        }
    }
}