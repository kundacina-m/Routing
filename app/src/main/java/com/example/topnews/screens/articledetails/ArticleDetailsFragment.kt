package com.example.topnews.screens.articledetails

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.core.app.SharedElementCallback
import androidx.core.view.ViewCompat
import androidx.transition.TransitionInflater
import base.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.screens.Article
import com.example.topnews.utils.Constants.MAP_SOURCE_KEY_NAME
import com.example.topnews.utils.Constants.PARCEL_FOR_ARTICLE_DETAILS
import com.example.topnews.utils.Constants.TRANSITION_ENABLED
import com.example.topnews.widget.ReadLaterWidget
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_frame.bottom_navigation
import kotlinx.android.synthetic.main.fragment_article_details.appbar
import kotlinx.android.synthetic.main.fragment_article_details.article_app_bar
import kotlinx.android.synthetic.main.fragment_article_details.fab
import kotlinx.android.synthetic.main.fragment_article_details.ivArticleImage
import kotlinx.android.synthetic.main.fragment_article_details.linkToWeb
import kotlinx.android.synthetic.main.fragment_article_details.tvAuthor
import kotlinx.android.synthetic.main.fragment_article_details.tvContent
import kotlinx.android.synthetic.main.fragment_article_details.tvDescription
import kotlinx.android.synthetic.main.fragment_article_details.tvPublishedAt
import kotlinx.android.synthetic.main.fragment_article_details.tvSource
import kotlinx.android.synthetic.main.fragment_article_details.tvTitle
import kotlin.properties.Delegates

class ArticleDetailsFragment : BaseFragment<ArticleDetailsViewModel>() {

	override fun getLayoutId(): Int = R.layout.fragment_article_details
	override fun getClassTypeVM(): Class<ArticleDetailsViewModel> = ArticleDetailsViewModel::class.java

	private lateinit var menu: Menu

	private val dataItem by lazy {
		arguments?.getParcelable(PARCEL_FOR_ARTICLE_DETAILS) as Article
	}

	private var articleInDB by Delegates.observable(false) { _, _, isInDb ->
		fab.isActivated = isInDb
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
		}
	}

	override fun initView() {
		viewModel.checkIfArticleExistsInDB(dataItem) {
			articleInDB = it
		}

		activity?.bottom_navigation?.visibility = View.GONE
		actionBarSetup()
		setTransitionElements()


		if (arguments?.getBoolean(TRANSITION_ENABLED)!!) setOnTransitionEnterEndListener()
		else fillViewWithData()

		setCollapsedImgListener()

		fab.setOnClickListener {
			if (articleInDB) removeArticleFromFavourites(dataItem)
			else addArticleToFavourites(dataItem)
		}

	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		menu.clear()
		inflater.inflate(R.menu.details_menu, menu)
		this.menu = menu
		setOnMenuItemClickListener()
		super.onCreateOptionsMenu(menu, inflater)
	}

	override fun onDestroyView() {
		activity?.bottom_navigation?.visibility = View.VISIBLE
		super.onDestroyView()
	}

	private fun actionBarSetup() {
		setActionBar(article_app_bar, true)
		actionBar?.title = ""
	}

	private fun setTransitionElements() {
		ViewCompat.setTransitionName(ivArticleImage, dataItem.publishedAt)
		ViewCompat.setTransitionName(tvTitle, dataItem.publishedAt + "title")
		tvTitle.text = dataItem.title

		val options = RequestOptions()
			.centerCrop()
			.error(R.drawable.error_img)
			.diskCacheStrategy(DiskCacheStrategy.ALL)
			.priority(Priority.HIGH)

		Glide.with(activity!!).load(dataItem.urlToImage).apply(options)
			.into(ivArticleImage)
	}

	private fun setCollapsedImgListener() {
		appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
			menu.findItem(R.id.shareArticle).isVisible = Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0
		})
	}

	private fun setOnMenuItemClickListener() {
		menu.findItem(R.id.shareArticle).setOnMenuItemClickListener {
			val shareIntent = Intent().apply {
				action = Intent.ACTION_SEND
				type = "text/plain"
				putExtra(Intent.EXTRA_TEXT, dataItem.url)
			}
			startActivity(Intent.createChooser(shareIntent, "choose one"))
			true
		}
	}

	private fun fillViewWithData() {
		tvSource.text = dataItem.source.get(key = MAP_SOURCE_KEY_NAME)
		tvDescription.text = dataItem.description
		tvContent.text = dataItem.content
		tvPublishedAt.text = dataItem.publishedAt
		tvAuthor.text = dataItem.author
		linkToWeb.text = dataItem.url
	}

	private fun addArticleToFavourites(article: Article) {
		viewModel.insertArticleInDb(article) {
			if (it) {
				ReadLaterWidget.WidgetRefresher.sendRefreshBroadcast(context!!)
				Toast.makeText(activity, "Article successfully added to favourites!", Toast.LENGTH_SHORT).show()
				articleInDB = !articleInDB

			}
		}
	}

	private fun removeArticleFromFavourites(article: Article) {
		viewModel.removeArticleFromDb(article) {
			if (it) {
				ReadLaterWidget.WidgetRefresher.sendRefreshBroadcast(context!!)
				Toast.makeText(activity, "Article successfully removed from favourites!", Toast.LENGTH_SHORT).show()
				articleInDB = !articleInDB

			}
		}
	}

	private fun setOnTransitionEnterEndListener() {
		setEnterSharedElementCallback(object : SharedElementCallback() {

			override fun onSharedElementEnd(
				sharedElementNames: MutableList<String>?,
				sharedElements: MutableList<View>?,
				sharedElementSnapshots: MutableList<View>?
			) {
				fillViewWithData()
				super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
			}
		})
	}

}
