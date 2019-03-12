package com.example.topnews.screens.topnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.*
import com.example.topnews.screens.Constants.ARG_AUTHOR_BUNDLE
import com.example.topnews.screens.Constants.ARG_CONTENT_BUNDLE
import com.example.topnews.screens.Constants.ARG_DESCRIPTION_BUNDLE
import com.example.topnews.screens.Constants.ARG_IMG_URL_BUNDLE
import com.example.topnews.screens.Constants.ARG_PUBLISHED_AT_BUNDLE
import com.example.topnews.screens.Constants.ARG_SOURCE_BUNDLE
import com.example.topnews.screens.Constants.ARG_TITLE_BUNDLE
import com.example.topnews.screens.Constants.ARG_URL_WEB_BUNDLE
import com.example.topnews.screens.Constants.MAP_SOURCE_KEY_NAME
import com.example.topnews.screens.Utils.WrappedAdapter
import kotlinx.android.synthetic.main.fragment_top_news.*


class TopNewsFragment : Fragment(), OnRVItemClickListener<Article> {

    private lateinit var viewModel: ArticleViewModel
    private lateinit var adapterTopNews: WrappedAdapter<Article>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupRecyclerView()
        setViewModel()

    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        viewModel.getArticles().observe(this, Observer { listArticles ->
            listArticles?.let { adapterTopNews.setData(it) }
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(context, 2)
        rwTopNews.layoutManager = layoutManager
        rwTopNews.adapter = adapterTopNews
    }

    private fun setupAdapter() {
        adapterTopNews = WrappedAdapter(R.layout.item_top_news, this)
    }

    override fun itemClicked(dataItem: Article) {
        val bundle = Bundle()

        bundle.putString(ARG_IMG_URL_BUNDLE, dataItem.imageUrl)
        bundle.putString(ARG_SOURCE_BUNDLE, dataItem.source[MAP_SOURCE_KEY_NAME])
        bundle.putString(ARG_TITLE_BUNDLE, dataItem.title)
        bundle.putString(ARG_DESCRIPTION_BUNDLE, dataItem.description)
        bundle.putString(ARG_CONTENT_BUNDLE, dataItem.content)
        bundle.putString(ARG_PUBLISHED_AT_BUNDLE, dataItem.publishedAt)
        bundle.putString(ARG_AUTHOR_BUNDLE, dataItem.author)
        bundle.putString(ARG_URL_WEB_BUNDLE, dataItem.urlToArticle)

        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.action_topNewsFragment_to_articleDetailsFragment, bundle)
    }
}
