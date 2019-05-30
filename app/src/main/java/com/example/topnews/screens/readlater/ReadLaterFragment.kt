package com.example.topnews.screens.readlater

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseFragment
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.screens.main.MainActivity
import com.example.topnews.utils.Constants.PARCEL_FOR_ARTICLE_DETAILS
import kotlinx.android.synthetic.main.fragment_read_later.readLaterRecyclerView
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top
import javax.inject.Inject

class ReadLaterFragment : BaseFragment<ReadLaterViewModel>() {

	@Inject lateinit var adapter: ReadLaterAdapter

	private lateinit var menu: Menu

	override fun getLayoutId(): Int = R.layout.fragment_read_later
	override fun getClassTypeVM(): Class<ReadLaterViewModel> = ReadLaterViewModel::class.java

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setObservers()
		setHasOptionsMenu(true)

		(activity!! as MainActivity).voidSelection = { this@ReadLaterFragment.inSelectionMode() }
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
		setMenuClickListeners()
		super.onCreateOptionsMenu(menu, inflater)
	}

	private fun setMenuClickListeners() {

		menu.findItem(R.id.removeSelected).setOnMenuItemClickListener {
			viewModel.removeSelected(adapter.checkedArticles)
			adapter.checkedArticles = arrayListOf()
			adapter.selectionInProgress = false
			showMenu(false)
			true
		}
	}

	fun showMenu(visibilityRemove: Boolean) {
		menu.findItem(R.id.removeSelected).isVisible = visibilityRemove
		menu.findItem(R.id.search).isVisible = !visibilityRemove
	}

	private fun setObservers() {
		viewModel.getArticles().observe(this@ReadLaterFragment, Observer { it ->
			it?.let { adapter.submitList(it) }
		})
	}

	private fun setupRecyclerView() =

		readLaterRecyclerView.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = this@ReadLaterFragment.adapter
		}

	private fun inSelectionMode(): Boolean {

		return if (!adapter.selectionInProgress) {
			false
		} else {
			adapter.selectionInProgress = false
			; true
		}
	}

	fun onItemClick(dataItem: Article) =
		navigateToArticleDetails(Bundle().apply { putParcelable(PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

	private fun navigateToArticleDetails(bundle: Bundle) =
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.action_readLaterFragment_to_articleDetailsFragment, bundle)

}