package com.example.topnews.screens.readlater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.screens.dummyDataForReadLater

class ReadLaterAdapter(private val context: Context,private val listener: OnItemClickListener): RecyclerView.Adapter<ReadLaterAdapter.ViewHolder>() {

    private var data = mutableListOf<dummyDataForReadLater>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_read_later, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = data[position]

        holder.mTitle.text = dataItem.title
        holder.mSource.text = dataItem.source
        holder.mPublishedAt.text = dataItem.publishedAt
        Glide.with(context).load(dataItem.image).apply(RequestOptions().override(400, 600)).into(holder.mImage)

        holder.bind(dataItem,listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(dataList: MutableList<dummyDataForReadLater>){
        data = dataList
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var mTitle = itemView.findViewById<TextView>(R.id.tvTitleReadLater)!!
        var mSource = itemView.findViewById<TextView>(R.id.tvSourceLaterRead)!!
        var mPublishedAt = itemView.findViewById<TextView>(R.id.tvPublishTimeReadLater)!!
        var mImage = itemView.findViewById<ImageView>(R.id.ivImgReadLater)!!

        fun bind(dataItem: dummyDataForReadLater,listener: OnItemClickListener){
            itemView.setOnClickListener {
                listener.onItemClick(dataItem)
            }
        }


    }

    interface OnItemClickListener {
        fun onItemClick(dataItem: dummyDataForReadLater)
    }

}