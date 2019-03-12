package com.example.topnews.screens.readlater


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.*

import kotlinx.android.synthetic.main.fragment_read_later.*

class ReadLaterFragment : Fragment(), OnRVItemClickListener<Article> {

    private lateinit var viewModel: ArticleViewModel
    private lateinit var adapterReadLater: WrappedAdapter<Article>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_read_later, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupReadLaterAdapter()
        setupRecyclerView()
        setViewModel()

    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        viewModel.getArticles().observe(this, Observer { listArticles ->
            listArticles?.let { adapterReadLater.setData(it) }
        })
    }

    private fun setupRecyclerView() {
        readLaterRecyclerView.layoutManager = LinearLayoutManager(context)
        readLaterRecyclerView.adapter = adapterReadLater

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(getDrawable(context!!, R.drawable.divider)!!)
        readLaterRecyclerView.addItemDecoration(divider)
    }

    private fun setupReadLaterAdapter() {
        adapterReadLater = WrappedAdapter(R.layout.item_read_later, this)

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
            .navigate(R.id.action_readLaterFragment_to_articleDetailsFragment, bundle)
    }


}
