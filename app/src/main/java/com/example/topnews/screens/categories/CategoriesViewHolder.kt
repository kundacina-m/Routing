package com.example.topnews.screens.categories

import android.view.View
import base.BaseViewHolder
import kotlinx.android.synthetic.main.item_section.view.*

class CategoriesViewHolder(itemView: View): BaseViewHolder<String>(itemView) {

    override fun bind(dataItem: String) {
        itemView.tvSection.text = dataItem
    }
}