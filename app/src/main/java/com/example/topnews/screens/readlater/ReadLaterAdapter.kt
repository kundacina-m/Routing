package com.example.topnews.screens.readlater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import kotlinx.android.synthetic.main.item_read_later.view.*

class ReadLaterAdapter: RecyclerView.Adapter<ReadLaterAdapter.ViewHolder>() {

    private var data = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_read_later, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = data[position]

        holder.bind(dataItem)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(dataList: MutableList<Article>){
        data = dataList
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(dataItem: Article){

            setupViewWithData(dataItem)
            setupClickListeners(dataItem)
        }

        private fun setupViewWithData(dataItem: Article){
            itemView.tvTitleReadLater.text = dataItem.title
            itemView.tvSourceLaterRead.text = dataItem.source
            itemView.tvPublishTimeReadLater.text = dataItem.publishedAt
            Glide.with(itemView.context).load(dataItem.imageUrl).apply(RequestOptions().override(400, 600)).into(itemView.ivImgReadLater)

        }

        private fun setupClickListeners(dataItem: Article){
            itemView.setOnClickListener {
                Toast.makeText(it.context,dataItem.title,Toast.LENGTH_LONG)
            }
        }


    }

}