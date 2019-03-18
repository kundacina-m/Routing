package com.example.topnews.screens.categories

import android.view.View
import base.BaseViewHolder
import kotlinx.android.synthetic.main.item_sestionitem.view.*

class ItemViewHolder(itemView: View): BaseViewHolder<RV>(itemView) {

    override fun bind(dataItem: RV) {
        val item = dataItem as SectionItem
        itemView.tvSectionItemTitle.text = item.title
        itemView.tvSectionItemNum.text = item.num.toString()
    }
}