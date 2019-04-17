package com.example.topnews.screens.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.os.Bundle
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.topnews.R
import com.example.topnews.data.db.sqlite.DBContract
import com.example.topnews.data.db.sqlite.DBHelper
import com.example.topnews.data.model.Article
import com.example.topnews.utils.Constants

class MyWidgetRemoteViewsFactory(private val mContext: Context) :
	RemoteViewsService.RemoteViewsFactory {
	private var mCursor: Cursor? = null

	override fun onCreate() {
	}

	override fun onDataSetChanged() {

		if (mCursor != null) {
			mCursor!!.close()
		}

		val identityToken = Binder.clearCallingIdentity()

		val db = DBHelper(mContext).readableDatabase
		mCursor = db.query(DBContract.ArticleEntry.TABLE_NAME, null, null, null, null, null, null)
		mCursor!!.moveToFirst()
		mCursor!!.count

		Binder.restoreCallingIdentity(identityToken)

	}

	override fun onDestroy() {
		if (mCursor != null) {
			mCursor!!.close()
		}
	}

	override fun getCount(): Int {
		return if (mCursor == null) 0 else mCursor!!.count
	}

	override fun getViewAt(position: Int): RemoteViews? {
		if (position == AdapterView.INVALID_POSITION ||
			mCursor == null || !mCursor!!.moveToPosition(position)
		) {
			return null
		}

		val rv = setUpView()
		rv.setOnClickFillInIntent(R.id.tvTitleWidget, createIntentWithBundle())

		return rv
	}

	override fun getLoadingView(): RemoteViews? {
		return null
	}

	override fun getViewTypeCount(): Int {
		return 1
	}

	override fun getItemId(position: Int): Long {
		return if (mCursor!!.moveToPosition(position))
			mCursor!!.getLong(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_PUBLISHED_AT))
		else
			return position.toLong()
	}

	override fun hasStableIds(): Boolean {
		return false
	}

	private fun setUpView(): RemoteViews {
		val rv = RemoteViews(mContext.packageName, R.layout.list_widget_item)
		rv.setTextViewText(
			R.id.tvTitleWidget,
			mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_TITLE))
		)
		return rv
	}

	private fun createIntentWithBundle(): Intent {
		val extras = Bundle()
		extras.putParcelable(Constants.PARCEL_FOR_ARTICLE_DETAILS, getArticleObj())
		val fillInIntent = Intent()
		fillInIntent.putExtras(extras)
		return fillInIntent
	}

	private fun getArticleObj(): Article {
		return Article(
			hashMapOf(Constants.MAP_SOURCE_KEY_NAME to mCursor!!.getString(
				mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_SOURCE))),
			mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_AUTHOR)),
			mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_TITLE)),
			mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_DESCRIPTION)),
			mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_URL)),
			mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_URL_TO_IMAGE)),
			mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_PUBLISHED_AT)),
			mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_CONTENT))
		)
	}

}
