package com.example.topnews.screens.categories

import android.view.View
import base.BaseViewHolder
import kotlinx.android.synthetic.main.item_section.view.*

class CategoriesViewHolder(itemView: View): BaseViewHolder<RV>(itemView) {

    override fun bind(dataItem: RV) {
        val item = dataItem as Section
        itemView.tvSection.text = item.name
    }
}