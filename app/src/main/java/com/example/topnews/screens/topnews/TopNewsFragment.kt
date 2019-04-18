package com.example.topnews.screens.topnews

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import base.BaseFragment
import com.example.topnews.R
import com.example.topnews.data.model.Article
import com.example.topnews.domain.RequestError
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.domain.WrappedResponse.OnSuccess
import com.example.topnews.utils.Constants.PARCEL_FOR_ARTICLE_DETAILS
import com.example.topnews.utils.Constants.TRANSITION_ENABLED
import kotlinx.android.synthetic.main.fragment_top_news.rwTopNews
import kotlinx.android.synthetic.main.toolbar_default.toolbar_top

class TopNewsFragment : BaseFragment<TopNewsViewModel>(), TopNewsAdapter.onClickTransition {

	private val adapterTopNews: TopNewsAdapter by lazy {
		TopNewsAdapter().apply {
			onClickWithTransition = this@TopNewsFragment::onItemClickWithImgTransition
		}
	}

	override fun getLayoutId(): Int = R.layout.fragment_top_news
	override fun getClassTypeVM(): Class<TopNewsViewModel> = TopNewsViewModel::class.java

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setObservers()

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
		}
		sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
	}

	override fun initView() {
		actionBarSetup()
		setupRecyclerView()
		fetchData()

	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.default_menu, menu)
		handleSearchMenu(menu.findItem(R.id.search))
		super.onCreateOptionsMenu(menu, inflater)
	}

	private fun actionBarSetup() {
		setActionBar(toolbar_top)
		actionBar?.title = getString(R.string.topNews)
	}

	private fun setObservers() =
		viewModel.articles
			.observe(this, Observer {
				if (it is OnSuccess) adapterTopNews.setData(it.item) else handleError(it as OnError)
			})

	private fun fetchData() = viewModel.getArticles()

	private fun setupRecyclerView() =
		rwTopNews.apply {
			layoutManager = GridLayoutManager(context, 2)
			adapter = adapterTopNews
		}

	override fun onItemClickWithImgTransition(dataItem: Article, img: ImageView, title: TextView) {
		val extras = FragmentNavigator.Extras.Builder()
			.addSharedElement(img, ViewCompat.getTransitionName(img)!!)
			.addSharedElement(title, ViewCompat.getTransitionName(title)!!)
			.build()

		navigateToArticleDetails(Bundle().apply {
			putParcelable(PARCEL_FOR_ARTICLE_DETAILS, dataItem)
			putBoolean(TRANSITION_ENABLED, true)
		}, extras)
	}

	private fun navigateToArticleDetails(bundle: Bundle, extras: FragmentNavigator.Extras) =
		Navigation.findNavController(activity!!, R.id.nav_host_fragment)
			.navigate(R.id.action_topNewsFragment_to_articleDetailsFragment, bundle, null, extras)

	private fun handleError(onError: OnError<List<Article>>) =
		when (onError.error) {
			is RequestError.UnknownError -> Log.d(TAG, "handleError: Unknown ")
			is RequestError.HttpError -> Log.d(TAG, "handleError: Http")
			is RequestError.NoInternetError -> Log.d(TAG, "handleError: No Internet")
			is RequestError.ServerError -> Log.d(TAG, "handleError: Server")
		}

}