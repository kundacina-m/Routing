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
import kotlinx.android.synthetic.main.item_top_news.view.*

class TopNewsAdapter : RecyclerView.Adapter<TopNewsAdapter.ViewHolder>() {

    private var data = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_news, parent, false))
    }

    override fun onBindViewHolder(holder: TopNewsAdapter.ViewHolder, position: Int) {
        val dataItem = data[position]
        holder.bind(dataItem)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(list: MutableList<Article>) {
        data = list
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(dataItem: Article) {

            itemView.tvArticleTitle.text = dataItem.title
            Glide.with(itemView.context).load(dataItem.imageUrl).apply(RequestOptions().override(400, 600))
                .into(itemView.ivArticleImage)
            setUpClickListener(dataItem)
        }

        private fun setUpClickListener(dataItem: Article) {
            itemView.btReadLater.setOnClickListener {
                Toast.makeText(itemView.context, dataItem.title, Toast.LENGTH_LONG).show()
            }

            itemView.setOnClickListener {
                it.setOnClickListener {
                    Toast.makeText(it.context, "${dataItem.title} not star", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}