package com.example.topnews.screens.topnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.screens.Article
import com.example.topnews.screens.OnArticleClickListener
import kotlinx.android.synthetic.main.item_top_news.view.*

class TopNewsAdapter : RecyclerView.Adapter<TopNewsAdapter.ViewHolder>() {

    private var data = mutableListOf<Article>()
    private var callback : OnArticleClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_news, parent, false))
    }

    override fun onBindViewHolder(holder: TopNewsAdapter.ViewHolder, position: Int) {
        val dataItem = data[position]
        holder.bind(dataItem)
        holder.setUpClickListener(dataItem,callback!!)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(list: MutableList<Article>) {
        data = list
    }

    fun setListener(listener: OnArticleClickListener){
        callback = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(dataItem: Article) {

            itemView.tvArticleTitle.text = dataItem.title
            Glide.with(itemView.context).load(dataItem.imageUrl).apply(RequestOptions().override(400, 600))
                .into(itemView.ivArticleImage)
        }

        fun setUpClickListener(dataItem: Article, listener: OnArticleClickListener) {
            itemView.btReadLater.setOnClickListener {
            }

            itemView.setOnClickListener {
                it.setOnClickListener {
                    listener.articleClicked(dataItem)
                }
            }
        }

    }

}