package com.example.topnews.screens.readlater


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.FakeData

import kotlinx.android.synthetic.main.fragment_read_later.*

class ReadLaterFragment : Fragment() {

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
        adapter.setData(FakeData.fetchData())
        return adapter
    }

}
