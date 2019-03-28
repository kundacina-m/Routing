package com.example.topnews.screens.articledetails


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.topnews.R
import com.example.topnews.ReadLaterWidget
import com.example.topnews.screens.Article
import com.example.topnews.utils.Constants.MAP_SOURCE_KEY_NAME
import com.example.topnews.utils.Constants.PARCEL_FOR_ARTICLE_DETAILS
import com.example.topnews.db.ArticleDaoImpl
import kotlinx.android.synthetic.main.fragment_article_details.*

class ArticleDetailsFragment : Fragment() {

    private val articleDAO by lazy {
        ArticleDaoImpl()
    }

    private val dataItem by lazy {
        arguments?.getParcelable(PARCEL_FOR_ARTICLE_DETAILS) as Article
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillViewWithData()
        btReadLater.setOnClickListener {
            addArticleToFavourites(dataItem)
        }


    }

    private fun fillViewWithData() {
        tvSource.text = dataItem.source.get(key = MAP_SOURCE_KEY_NAME)
        tvTitle.text = dataItem.title
        tvDescription.text = dataItem.description
        tvContent.text = dataItem.content
        tvPublishedAt.text = dataItem.publishedAt
        tvAuthor.text = dataItem.author
        linkToWeb.text = dataItem.url

        Glide.with(activity!!).load(dataItem.urlToImage)
            .into(ivArticleImage)
    }

    private fun addArticleToFavourites(article: Article) {
        articleDAO.insertItem(article).executeAsync {
            if (it)
                Toast.makeText(activity, "Article successfully added to favourites!", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(activity, "Article successfully removed from favourites!", Toast.LENGTH_LONG).show()
            ReadLaterWidget.readLaterProvider.sendRefreshBroadcast(context!!)
        }

    }
}
