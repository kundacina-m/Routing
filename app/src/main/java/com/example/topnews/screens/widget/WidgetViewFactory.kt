package com.example.topnews.screens.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.topnews.App
import com.example.topnews.R
import com.example.topnews.data.model.Article
import com.example.topnews.utils.Constants
import io.reactivex.rxkotlin.subscribeBy

class MyWidgetRemoteViewsFactory(private val mContext: Context) :
	RemoteViewsService.RemoteViewsFactory {
	private var articles = arrayListOf<Article>()

	override fun onCreate() {
		App.injectArticleDao().getAllItems().subscribeBy {
			articles.clear()
			articles.addAll(it)
		}

	}

	override fun onDataSetChanged() {

		val identityToken = Binder.clearCallingIdentity()
		Binder.restoreCallingIdentity(identityToken)

	}

	override fun onDestroy() {

	}

	override fun getCount(): Int {
		return articles.size
	}

	override fun getViewAt(position: Int): RemoteViews? {

		val rv = setUpView(articles[position])
		rv.setOnClickFillInIntent(R.id.tvTitleWidget, createIntentWithBundle(articles[position]))

		return rv
	}

	override fun getLoadingView(): RemoteViews? {
		return null
	}

	override fun getViewTypeCount(): Int {
		return 1
	}

	override fun getItemId(position: Int): Long {
		return if (articles.isNotEmpty()) {
			articles[position].publishedAt.toLong()
		} else
			return position.toLong()
	}

	override fun hasStableIds(): Boolean {
		return false
	}

	private fun setUpView(article: Article): RemoteViews {
		val rv = RemoteViews(mContext.packageName, R.layout.list_widget_item)
		rv.setTextViewText(
			R.id.tvTitleWidget,
			article.title
		)
		return rv
	}

	private fun createIntentWithBundle(article: Article): Intent {
		val extras = Bundle()
		extras.putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS, article)
		val fillInIntent = Intent()
		fillInIntent.putExtras(extras)
		return fillInIntent
	}

}
