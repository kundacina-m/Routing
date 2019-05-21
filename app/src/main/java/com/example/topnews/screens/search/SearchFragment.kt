package com.example.topnews.screens.search

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseFragment
import base.BasePagedListAdapter
import com.example.topnews.R
import com.example.topnews.data.db.Article
import com.example.topnews.domain.RequestError
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.utils.Constants
import com.example.topnews.utils.Constants.ERROR_DATABASE
import com.example.topnews.utils.Constants.SHOW_NAV_BAR
import kotlinx.android.synthetic.main.activity_frame.bottom_navigation
import kotlinx.android.synthetic.main.fragment_search.rvSearchResults
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top

class SearchFragment : BaseFragment<SearchViewModel>(), BasePagedListAdapter.OnItemClickListener<Article> {

	private val navCtrl: NavController by lazy {
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
	}

	private val waitingTimeForKeyDown = 1000
	private var searchKeyword: String = ""

	private var searchTimer = object : SearchTimer(waitingTimeForKeyDown.toLong(), 500) {
		override fun onFinish() {
			updateSearchList()
		}
	}

	private val adapter: SearchAdapter by lazy {
		SearchAdapter().apply {
			oneClickListener = this@SearchFragment::onItemClick
		}
	}

	override fun getLayoutId() = R.layout.fragment_search
	override fun getClassTypeVM(): Class<SearchViewModel> = SearchViewModel::class.java

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.search_menu, menu)
		setupSearchMenuItem(menu.findItem(R.id.search))
		super.onCreateOptionsMenu(menu, inflater)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigateUp()
		}
		return super.onOptionsItemSelected(item)
	}

	override fun initView() {

		activity?.bottom_navigation?.visibility = View.GONE

		setupRecyclerView()
		setupActionBar()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setObservers()
	}

	override fun onDestroy() {
		super.onDestroy()
		activity?.bottom_navigation?.visibility = View.VISIBLE
	}

	private fun setupActionBar() {
		setActionBar(toolbar_top)
		actionBar?.title = ""
	}

	private fun setupSearchMenuItem(searchItem: MenuItem) {
		searchItem.expandActionView()

		MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {
			override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true
			override fun onMenuItemActionCollapse(item: MenuItem?): Boolean = navCtrl.navigateUp()
		})

		val searchView = searchItem.actionView as SearchView
		setSearchViewListener(searchView)
	}

	private fun setSearchViewListener(searchView: SearchView) =
		searchView.apply {
			setOnQueryTextListener(object : SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String?): Boolean = false

				override fun onQueryTextChange(newText: String?): Boolean {
					searchKeyword = newText!!
					restartCountdownTimer(searchTimer)
					return false

				}

			})
			setOnSearchClickListener {
				navCtrl.navigate(R.id.searchFragment)
			}
		}

	private fun updateSearchList() =
		viewModel.queryForString(searchKeyword)

	private fun restartCountdownTimer(cntrForKeyUP: CountDownTimer) = cntrForKeyUP.apply {
		cancel()
		start()
	}

	private fun setupRecyclerView() =
		rvSearchResults.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = this@SearchFragment.adapter
		}

	private fun setObservers() {
		viewModel.articles.observe(this, Observer {
			adapter.submitList(it)
		})

		viewModel.onError.observe(this, Observer {
			handleError(it)
		})
	}

	override fun onItemClick(dataItem: Article) =
		navigateToArticleDetails(Bundle().apply { putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS, dataItem);
			putString(SHOW_NAV_BAR, SHOW_NAV_BAR)})

	private fun navigateToArticleDetails(bundle: Bundle) =
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.action_searchFragment_to_articleDetailsFragment, bundle)

	private fun handleError(onError: OnError<List<Article>>) =
		when (onError.error) {
			is RequestError.UnknownError -> Log.d(TAG, Constants.ERROR_UNKNOWN)
			is RequestError.HttpError -> Log.d(TAG, Constants.ERROR_HTTP)
			is RequestError.NoInternetError -> Log.d(TAG, Constants.ERROR_INTERNET)
			is RequestError.ServerError -> Log.d(TAG, Constants.ERROR_SERVER)
			is RequestError.DatabaseError -> Log.d(TAG, ERROR_DATABASE)
		}

}