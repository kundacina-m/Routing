package com.example.topnews.screens.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.Article
import com.example.topnews.screens.FakeData
import com.example.topnews.screens.OnArticleClickListener
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() ,OnArticleClickListener{

    private lateinit var adapter: SearchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvSearchResults.layoutManager = LinearLayoutManager(context)
        setupAdapter()
        rvSearchResults.adapter = adapter

    }

    private fun setupAdapter() {
        adapter = SearchAdapter()
        adapter.setListener(this)
    }

    fun updateAdapter(num: Int){
        adapter.setData(FakeData.fetchSearchedData(num))
        adapter.notifyDataSetChanged()
    }

    override fun articleClicked(dataItem: Article) {
        Toast.makeText(context, dataItem.title, Toast.LENGTH_LONG).show()
    }
}
