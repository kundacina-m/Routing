package com.example.topnews.screens.search

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import base.BaseFragment
import com.example.topnews.R
import com.example.topnews.data.model.Article
import com.example.topnews.domain.RequestError
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants
import kotlinx.android.synthetic.main.fragment_search.rvSearchResults
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top

class SearchFragment : BaseFragment<SearchViewModel>(), BaseAdapter.OnItemClickListener<Article> {

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

	private var loading = false

	private val adapterSearch: SearchAdapter by lazy {
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
		setupRecyclerView()
		setupActionBar()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setObservers()
	}

	private fun setupActionBar() {
		setActionBar(toolbar_top)
		actionBar?.title = ""
	}

	private fun setupSearchMenuItem(searchItem: MenuItem) {
		searchItem.expandActionView()

		MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {
			override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true
			override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
				navCtrl.navigateUp()
				return true
			}

		})

		val searchView = searchItem.actionView as SearchView
		setSearchViewListener(searchView)
	}

	private fun setSearchViewListener(searchView: SearchView) =
		searchView.apply {
			setOnQueryTextListener(object : SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String?): Boolean {
					return false
				}

				override fun onQueryTextChange(newText: String?): Boolean {
					return if (!newText?.isEmpty()!!) {
						searchKeyword = newText
						restartCountdownTimer(searchTimer)
						doOnTextChanged(newText)
						false
					} else true
				}

			})
			setOnSearchClickListener {
				navCtrl.navigate(R.id.searchFragment)
			}
		}

	private fun updateSearchList() =
		fetchData(searchKeyword)

	private fun doOnTextChanged(newText: String) {
		if (navCtrl.currentDestination!!.id != R.id.searchFragment)
			navCtrl.navigate(R.id.searchFragment)
		if (newText.isEmpty())
			navCtrl.navigateUp()
	}

	private fun restartCountdownTimer(cntrForKeyUP: CountDownTimer) = cntrForKeyUP.apply {
		cancel()
		start()
	}

	private fun setupRecyclerView() =
		rvSearchResults.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = adapterSearch
			addOnScrollListener(object : RecyclerView.OnScrollListener() {
				override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
					super.onScrolled(recyclerView, dx, dy)
					if (dy > 0) {
						val totalItemCount = layoutManager?.itemCount
						val pastVisibleItem = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

						if (totalItemCount != null && totalItemCount < viewModel.totalResults) {
							if ((totalItemCount <= pastVisibleItem + 5) && !loading) {
								loading = true
								viewModel.getArticlesForQueryOnScroll()
							}
						}
					}
				}
			})
		}

	private fun setObservers() = viewModel.getNetworkSearchResults().observe(this, Observer {
		if (it is OnSuccess) {
			adapterSearch.setData(it.item)
			loading = false
		} else (handleError(it as OnError))

	})

	private fun fetchData(searchKeyword: String) = viewModel.getArticlesForQuery(searchKeyword)

	override fun onItemClick(dataItem: Article) =
		navigateToArticleDetails(Bundle().apply { putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

	private fun navigateToArticleDetails(bundle: Bundle) =
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.action_searchFragment_to_articleDetailsFragment, bundle)

	private fun handleError(onError: OnError<List<Article>>) =
		when (onError.error) {
			is RequestError.UnknownError -> Log.d(TAG, "handleError: Unknown ")
			is RequestError.HttpError -> Log.d(TAG, "handleError: Http")
			is RequestError.NoInternetError -> Log.d(TAG, "handleError: No Internet")
			is RequestError.ServerError -> Log.d(TAG, "handleError: Server")
		}

}