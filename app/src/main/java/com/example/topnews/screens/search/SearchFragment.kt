package com.example.topnews.screens.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.FakeData
import com.example.topnews.screens.readlater.ReadLaterAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var adapter: ReadLaterAdapter

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

//        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//        divider.setDrawable(AppCompatResources.getDrawable(context!!, R.drawable.divider)!!)
//        rvSearchResults.addItemDecoration(divider)
    }

    private fun setupAdapter() {
        adapter = ReadLaterAdapter()
        updateAdapter()
    }

    fun updateAdapter(){
        adapter.setData(FakeData.fetchData())
    }
}
