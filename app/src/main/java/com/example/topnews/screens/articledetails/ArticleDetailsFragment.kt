package com.example.topnews.screens.articledetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.topnews.R
import kotlinx.android.synthetic.main.fragment_article_details.*

class ArticleDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val actionBar = activity!!.actionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvSource.text = arguments?.getString("source")
        tvTitle.text = arguments?.getString("title")
        tvDescription.text = arguments?.getString("description")
        tvContent.text = arguments?.getString("content")
        tvPublishedAt.text = arguments?.getString("publishedAt")
        tvAuthor.text = arguments?.getString("author")
        linkToWeb.text = arguments?.getString("urlWeb")

        Glide.with(activity!!).load(arguments?.getString("urlImg"))
            .into(ivArticleImage)

    }

}
