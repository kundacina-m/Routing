package com.example.topnews.screens.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import java.lang.RuntimeException

class CategoriesAdapter : BaseAdapter<RV>() {

    override fun getItemViewType(position: Int): Int {
        return getItemOnPosition(position).type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SECTION -> CategoriesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false))
            ITEM -> ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sestionitem,parent,false))
            else -> throw RuntimeException()
        }
    }

//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        (holder as ViewHolderAdapterBinder<RV>).bind(data[position])
//    }
}