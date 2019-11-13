package com.example.topnews.screens.article.details

import android.view.Menu
import android.view.MenuInflater
import androidx.navigation.fragment.navArgs
import base.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.topnews.R
import com.example.topnews.utils.asString
import kotlinx.android.synthetic.main.fragment_article_details.article_app_bar
import kotlinx.android.synthetic.main.fragment_article_details.ivArticleImage
import kotlinx.android.synthetic.main.fragment_article_details.linkToWeb
import kotlinx.android.synthetic.main.fragment_article_details.tvAuthor
import kotlinx.android.synthetic.main.fragment_article_details.tvContent
import kotlinx.android.synthetic.main.fragment_article_details.tvDescription
import kotlinx.android.synthetic.main.fragment_article_details.tvPublishedAt
import kotlinx.android.synthetic.main.fragment_article_details.tvSource
import kotlinx.android.synthetic.main.fragment_article_details.tvTitle

class ArticleDetailsFragment : BaseFragment<ArticleDetailsViewModel, ArticleDetailsRoutes>() {

	private val args: ArticleDetailsFragmentArgs by navArgs()

	override fun setObservers() {}

	override fun getLayoutId(): Int = R.layout.fragment_article_details

	override fun initView() {

		setActionBar(article_app_bar, true)
		fillViewWithData()
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		menu.clear()
		inflater.inflate(R.menu.default_menu, menu)
		super.onCreateOptionsMenu(menu, inflater)
	}

	private fun fillViewWithData() {
		tvTitle.text = args.article.title
		tvSource.text = args.article.source
		tvDescription.text = args.article.description
		tvContent.text = args.article.content
		tvPublishedAt.text = args.article.publishedAt?.asString(context!!)
		tvAuthor.text = args.article.author
		linkToWeb.text = args.article.url

		val options = RequestOptions()
			.centerCrop()
			.error(R.drawable.ic_error_image)
			.diskCacheStrategy(DiskCacheStrategy.ALL)
			.priority(Priority.HIGH)

		Glide.with(activity!!).load(args.article.urlToImage).apply(options)
			.into(ivArticleImage)

		showToast(args.firstString + " and " + args.secondString + "\n ${args.list.asList()}")
	}

}
