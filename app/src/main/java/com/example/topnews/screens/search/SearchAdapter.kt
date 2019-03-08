package com.example.topnews.screens.search

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
import kotlinx.android.synthetic.main.item_read_later.view.*
import kotlinx.android.synthetic.main.item_search_result.view.*

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var data = mutableListOf<Article>()
    private var callback : OnArticleClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = data[position]

        holder.bind(dataItem)
        holder.setupClickListener(dataItem,callback!!)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(dataList: MutableList<Article>){
        data = dataList
    }

    fun setListener(listener: OnArticleClickListener){
        callback = listener
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(dataItem: Article){

            setupViewWithData(dataItem)
        }

        private fun setupViewWithData(dataItem: Article){
            itemView.tvTitle.text = dataItem.title
            itemView.tvSource.text = dataItem.source.getValue("name")
            itemView.tvPublishTime.text = dataItem.publishedAt
            Glide.with(itemView.context).load(dataItem.imageUrl).apply(RequestOptions().override(400, 600)).into(itemView.ivImg)

        }

        fun setupClickListener(dataItem: Article,listener: OnArticleClickListener){
            itemView.setOnClickListener {
                listener.articleClicked(dataItem)
            }
        }


    }

}