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
import com.example.topnews.screens.Utils.BundleHolder
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
        navigateToArticleDetails(BundleHolder.getBundleForDetails(dataItem))
    }

    private fun navigateToArticleDetails(bundle: Bundle){
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.action_topNewsFragment_to_articleDetailsFragment, bundle)
    }

}
