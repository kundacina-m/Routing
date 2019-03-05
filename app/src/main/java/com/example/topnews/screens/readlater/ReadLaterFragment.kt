package com.example.topnews.screens.readlater


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import com.example.topnews.screens.dummyDataForReadLater
import com.example.topnews.screens.fakeData
import kotlinx.android.synthetic.main.fragment_read_later.*

class ReadLaterFragment : Fragment(), ReadLaterAdapter.OnItemClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_read_later, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupReadLaterAdapter(): ReadLaterAdapter{
        val adapter = ReadLaterAdapter(context!!,this)
        adapter.setData(fakeData.fakeData)
        return adapter
    }

    private fun setupRecyclerView(){
        readLaterRecyclerView.layoutManager = LinearLayoutManager(context)
        readLaterRecyclerView.adapter = setupReadLaterAdapter()

    }

    override fun onItemClick(dataItem: dummyDataForReadLater) {
        Toast.makeText(context, dataItem.title,Toast.LENGTH_LONG).show()
    }

}
