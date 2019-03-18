package com.example.topnews.screens.readlater


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.Article
import com.example.topnews.screens.FakeData
import com.example.topnews.screens.OnArticleClickListener

import kotlinx.android.synthetic.main.fragment_read_later.*

class ReadLaterFragment : Fragment(),OnArticleClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_read_later, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        readLaterRecyclerView.layoutManager = LinearLayoutManager(context)
        readLaterRecyclerView.adapter = setupReadLaterAdapter()

        val divider = DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
        divider.setDrawable(getDrawable(context!!,R.drawable.divider)!!)
        readLaterRecyclerView.addItemDecoration(divider)
    }

    private fun setupReadLaterAdapter(): ReadLaterAdapter {
        val adapter = ReadLaterAdapter()
        adapter.setListener(this)
        adapter.setData(FakeData.fetchData())
        return adapter
    }

    override fun articleClicked(dataItem: Article) {
        val bundle = Bundle()

        bundle.putString("urlImg",dataItem.imageUrl)
        bundle.putString("source",dataItem.source.get("name"))
        bundle.putString("title",dataItem.title)
        bundle.putString("description",dataItem.description)
        bundle.putString("content",dataItem.content)
        bundle.putString("publishedAt",dataItem.publishedAt)
        bundle.putString("author",dataItem.author)
        bundle.putString("urlWeb",dataItem.urlToArticle)

        Navigation.findNavController(activity!!,R.id.nav_host_fragment).navigate(R.id.action_readLaterFragment_to_articleDetailsFragment,bundle)    }


}
