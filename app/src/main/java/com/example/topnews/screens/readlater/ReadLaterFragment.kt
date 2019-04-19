package com.example.topnews.screens.readlater

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.Observer
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
import com.example.topnews.screens.home.FrameActivity
import com.example.topnews.screens.widget.ReadLaterWidget
import com.example.topnews.utils.Constants
import com.example.topnews.utils.Constants.PARCEL_FOR_ARTICLE_DETAILS
import kotlinx.android.synthetic.main.fragment_read_later.readLaterRecyclerView
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top

class ReadLaterFragment : BaseFragment<ReadLaterViewModel>(), BaseAdapter.OnItemClickListener<Article>,
						  ReadLaterAdapter.PopUpMenu {

	private var loading = false
	private lateinit var menu: Menu

	private val adapterReadLater by lazy {
		ReadLaterAdapter().apply {
			oneClickListener = this@ReadLaterFragment::onItemClick
			handleMenu = this@ReadLaterFragment::showMenu
		}
	}

	override fun getLayoutId(): Int = R.layout.fragment_read_later
	override fun getClassTypeVM(): Class<ReadLaterViewModel> = ReadLaterViewModel::class.java

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setObservers()
		setHasOptionsMenu(true)

		(activity!! as FrameActivity).voidSelection = { this@ReadLaterFragment.inSelectionMode() }
	}

	override fun initView() {
		actionBarSetup()
		setupRecyclerView()
		fetchData()

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

	private fun setObservers() {
		viewModel.getFavouritesFromDB().observe(this@ReadLaterFragment, Observer {
			if (it is OnSuccess) {
				adapterReadLater.setData(it.item)
				loading = false
			} else handleError(it as OnError)
		})
	}

	private fun setMenuClickListeners() {
		menu.findItem(R.id.selectAll).setOnMenuItemClickListener {
			adapterReadLater.checkAll = true
			showMenu(adapterReadLater.checkedArticles.isNotEmpty(), false)
			true
		}

		menu.findItem(R.id.removeSelected).setOnMenuItemClickListener {
			if (adapterReadLater.checkAll) {
				viewModel.removeWhenAllSelected(adapterReadLater.uncheckedArticles)
			} else {
				viewModel.removeSelected(adapterReadLater.checkedArticles)
			}
			adapterReadLater.uncheckedArticles = arrayListOf()
			adapterReadLater.checkedArticles = arrayListOf()
			showMenu(false, true)
			ReadLaterWidget.WidgetRefresher.sendRefreshBroadcast(context!!)
			true
		}
	}

	private fun fetchData() = viewModel.getArticlesFromDB()

	private fun setupRecyclerView() =
		readLaterRecyclerView.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = adapterReadLater
			addOnScrollListener(object : RecyclerView.OnScrollListener() {
				override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
					super.onScrolled(recyclerView, dx, dy)
					if (dy > 0 && !viewModel.endOfDB) {
						val totalItemCount = layoutManager?.itemCount
						val pastVisibleItem = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

						if (totalItemCount != null) {
							if ((totalItemCount <= pastVisibleItem + 4) && !loading) {
								loading = true
								viewModel.getArticlesFromDB()
							}
						}
					}
				}
			})
		}

	override fun showMenu(visibilityRemove: Boolean, visibilitySelectAll: Boolean) {
		menu.findItem(R.id.selectAll).isVisible = visibilitySelectAll
		menu.findItem(R.id.removeSelected).isVisible = visibilityRemove
		menu.findItem(R.id.search).isVisible = !(visibilityRemove.or(visibilitySelectAll))

	}

	private fun inSelectionMode(): Boolean {
		return if (!adapterReadLater.selectionInProgress) {
			false
		} else {
			adapterReadLater.apply {
				checkAll = false
				selectionInProgress = false
			}; true
		}
	}

	override fun onItemClick(dataItem: Article) =
		navigateToArticleDetails(Bundle().apply { putParcelable(PARCEL_FOR_ARTICLE_DETAILS, dataItem) })

	private fun navigateToArticleDetails(bundle: Bundle) =
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.action_readLaterFragment_to_articleDetailsFragment, bundle)

	private fun handleError(onError: OnError<List<Article>>) =
		when (onError.error) {
			is RequestError.UnknownError -> Log.d(TAG, Constants.ERROR_UNKNOWN)
			is RequestError.HttpError -> Log.d(TAG, Constants.ERROR_HTTP)
			is RequestError.NoInternetError -> Log.d(TAG, Constants.ERROR_INTERNET)
			is RequestError.ServerError -> Log.d(TAG, Constants.ERROR_SERVER)
		}

}