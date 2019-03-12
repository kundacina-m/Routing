package com.example.topnews.screens.articledetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.topnews.R
import com.example.topnews.screens.Constants.ARG_AUTHOR_BUNDLE
import com.example.topnews.screens.Constants.ARG_CONTENT_BUNDLE
import com.example.topnews.screens.Constants.ARG_DESCRIPTION_BUNDLE
import com.example.topnews.screens.Constants.ARG_IMG_URL_BUNDLE
import com.example.topnews.screens.Constants.ARG_PUBLISHED_AT_BUNDLE
import com.example.topnews.screens.Constants.ARG_SOURCE_BUNDLE
import com.example.topnews.screens.Constants.ARG_TITLE_BUNDLE
import com.example.topnews.screens.Constants.ARG_URL_WEB_BUNDLE
import com.example.topnews.screens.Constants.MAP_SOURCE_KEY_NAME
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

        tvSource.text = arguments?.getString(ARG_SOURCE_BUNDLE)
        tvTitle.text = arguments?.getString(ARG_TITLE_BUNDLE)
        tvDescription.text = arguments?.getString(ARG_DESCRIPTION_BUNDLE)
        tvContent.text = arguments?.getString(ARG_CONTENT_BUNDLE)
        tvPublishedAt.text = arguments?.getString(ARG_PUBLISHED_AT_BUNDLE)
        tvAuthor.text = arguments?.getString(ARG_AUTHOR_BUNDLE)
        linkToWeb.text = arguments?.getString(ARG_URL_WEB_BUNDLE)

        Glide.with(activity!!).load(arguments?.getString(ARG_IMG_URL_BUNDLE))
            .into(ivArticleImage)

    }

}
