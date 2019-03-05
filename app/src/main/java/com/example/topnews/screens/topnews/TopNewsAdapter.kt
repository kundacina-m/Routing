package com.example.topnews.screens.topnews

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
import com.example.topnews.screens.dummyDataForTopNews

class TopNewsAdapter(private val context: Context) : RecyclerView.Adapter<TopNewsAdapter.ViewHolder>() {

    private var data = mutableListOf<dummyDataForTopNews>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_news, parent, false))
    }

    override fun onBindViewHolder(holder: TopNewsAdapter.ViewHolder, position: Int) {
        val dataItem = data[position]

        holder.mTitle.text = dataItem.title
        Glide.with(context).load(dataItem.image).apply(RequestOptions().override(400, 600)).into(holder.mImage)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(list: MutableList<dummyDataForTopNews>) {
        data = list
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mTitle = itemView.findViewById<TextView>(R.id.gridArticleTitle)!!
        var mImage = itemView.findViewById<ImageView>(R.id.gridArticleImage)!!

    }
}