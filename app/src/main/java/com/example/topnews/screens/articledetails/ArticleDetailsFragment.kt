package com.example.topnews.screens.articledetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.topnews.R
import com.example.topnews.screens.Article
import com.example.topnews.screens.Constants.MAP_SOURCE_KEY_NAME
import com.example.topnews.screens.Constants.PARCEL_FOR_ARTICLE_DETAILS
import kotlinx.android.synthetic.main.fragment_article_details.*

class ArticleDetailsFragment : Fragment() {

    private val dataItem by lazy {
        arguments?.getParcelable(PARCEL_FOR_ARTICLE_DETAILS) as Article
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillViewWithData()
    }

    private fun fillViewWithData(){
        tvSource.text = dataItem.source.get(key = MAP_SOURCE_KEY_NAME)
        tvTitle.text = dataItem.title
        tvDescription.text = dataItem.description
        tvContent.text = dataItem.content
        tvPublishedAt.text = dataItem.publishedAt
        tvAuthor.text = dataItem.author
        linkToWeb.text = dataItem.urlToArticle

        Glide.with(activity!!).load(dataItem.imageUrl)
            .into(ivArticleImage)
    }

}
