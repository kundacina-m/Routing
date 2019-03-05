package com.example.topnews.screens.readlater


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.fakeData
import kotlinx.android.synthetic.main.fragment_read_later.*

class ReadLaterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_read_later, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupReadLaterAdapter(): ReadLaterAdapter{
        val adapter = ReadLaterAdapter(context!!)
        adapter.setData(fakeData.fakeData)
        return adapter
    }

    private fun setupRecyclerView(){
        readLaterRecyclerView.layoutManager = LinearLayoutManager(context)
        readLaterRecyclerView.adapter = setupReadLaterAdapter()

    }

}
