package com.example.topnews.screens.topnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.Article
import com.example.topnews.screens.FakeData
import com.example.topnews.screens.OnArticleClickListener
import kotlinx.android.synthetic.main.fragment_top_news.*


class TopNewsFragment : Fragment(), OnArticleClickListener{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(context, 2)
        rwTopNews.layoutManager = layoutManager

        rwTopNews.adapter = setupAdapter()
    }

    private fun setupAdapter(): TopNewsAdapter {
        val adapter = TopNewsAdapter()
        adapter.setListener(this)
        adapter.setData(FakeData.fetchData())

        return adapter
    }

    override fun articleClicked(dataItem: Article) {
        Toast.makeText(context, dataItem.title, Toast.LENGTH_LONG).show()
    }
}
