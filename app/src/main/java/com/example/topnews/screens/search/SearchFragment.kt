package com.example.topnews.screens.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), OnRVItemClickListener<Article> {

    private lateinit var adapterSearch: WrappedAdapter<Article>
    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupRecyclerView()
        setViewModel()
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

    }

    private fun setupRecyclerView() {
        rvSearchResults.layoutManager = LinearLayoutManager(context)
        rvSearchResults.adapter = adapterSearch

    }

    private fun setupAdapter() {
        adapterSearch = WrappedAdapter(R.layout.item_search_result, this)
    }

    fun updateAdapter(num: Int) {
        adapterSearch.setData(viewModel.getNumOfArticles(num).value!!)
    }

    override fun itemClicked(dataItem: Article) {
        val bundle = Bundle()

        bundle.putString("urlImg", dataItem.imageUrl)
        bundle.putString("source", dataItem.source.get("name"))
        bundle.putString("title", dataItem.title)
        bundle.putString("description", dataItem.description)
        bundle.putString("content", dataItem.content)
        bundle.putString("publishedAt", dataItem.publishedAt)
        bundle.putString("author", dataItem.author)
        bundle.putString("urlWeb", dataItem.urlToArticle)

        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(R.id.action_searchFragment_to_articleDetailsFragment, bundle)
    }
}
