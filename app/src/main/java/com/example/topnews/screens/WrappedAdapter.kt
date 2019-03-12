package com.example.topnews.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WrappedAdapter<T>(private val itemViewLayout: Int, private val listener: OnRVItemClickListener<T>? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = listOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderFactory.getRightViewHolder(
            LayoutInflater.from(parent.context).inflate(
                itemViewLayout,
                parent,
                false
            ), itemViewLayout
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        with(holder as ViewHolderAdapterBinder<T>) {
            bind(data[position], listener)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun setData(dataList: List<T>) {
        data = dataList
        notifyDataSetChanged()
    }

    internal interface ViewHolderAdapterBinder<T> {
        fun bind(dataItem: T, listener: OnRVItemClickListener<T>?)
    }


}