package com.example.topnews.screens.readlater

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseFragment
import base.BasePagedListAdapter
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.utils.Constants.PARCEL_FOR_ARTICLE_DETAILS
import kotlinx.android.synthetic.main.fragment_read_later.readLaterRecyclerView
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top

class ReadLaterFragment : BaseFragment<ReadLaterViewModel>(),
						  BasePagedListAdapter.OnItemClickListener<Article>{

	private lateinit var menu: Menu

	private val adapterReadLater by lazy {
		ReadLaterAdapter(viewModel).apply {
			oneClickListener = this@ReadLaterFragment::onItemClick
		}
	}

	override fun getLayoutId(): Int = R.layout.fragment_read_later
	override fun getClassTypeVM(): Class<ReadLaterViewModel> = ReadLaterViewModel::class.java

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setObservers()
		setHasOptionsMenu(true)

	}

	override fun initView() {
		actionBarSetup()
		setupRecyclerView()

	}

	private fun actionBarSetup() {
		setActionBar(toolbar_top)
		actionBar?.title = getString(R.string.readLater)
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		this.menu = menu
		inflater.inflate(R.menu.readlater_menu, menu)
		handleSearchMenu(menu.findItem(R.id.search))
		super.onCreateOptionsMenu(menu, inflater)
	}

	private fun setObservers() {
		viewModel.getArticles().observe(this@ReadLaterFragment, Observer { it ->
			it?.let { adapterReadLater.submitList(it) }
		})
	}

	private fun setupRecyclerView() =
		readLaterRecyclerView.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = adapterReadLater
		}

	override fun onItemClick(dataItem: Article) =
		navigateToArticleDetails(Bundle().apply { putParcelable(PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

	private fun navigateToArticleDetails(bundle: Bundle) =
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.action_readLaterFragment_to_articleDetailsFragment, bundle)

}