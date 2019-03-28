package com.example.topnews

import android.app.PendingIntent
import android.widget.RemoteViews
import android.content.Context
import android.widget.AdapterView
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViewsService
import com.example.topnews.screens.Article
import com.example.topnews.screens.frame.FrameActivity
import com.example.topnews.sqlite.DBContract
import com.example.topnews.sqlite.DBHelper
import com.example.topnews.utils.Constants


class MyWidgetRemoteViewsFactory(private val mContext: Context, intent: Intent) :
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

        val rv = RemoteViews(mContext.packageName, R.layout.list_widget_item)
        rv.setTextViewText(
            R.id.tvTitleWidget,
            mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_TITLE))
        )

        val articleArticle = Article(
            hashMapOf(Constants.MAP_SOURCE_KEY_NAME to mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_SOURCE))),
            mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_AUTHOR)),
            mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_TITLE)),
            mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_DESCRIPTION)),
            mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_URL)),
            mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_URL_TO_IMAGE)),
            mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_PUBLISHED_AT)),
            mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.ArticleEntry.COLUMN_CONTENT))
        )

        val intent = Intent(mContext, FrameActivity::class.java)
        intent.action = "SHOW_DETAILS"
        intent.putExtra(Constants.PARCEL_FOR_ARTICLE_DETAILS, articleArticle)

        val pendingIntent = PendingIntent.getActivity(mContext,0,intent,0)

        rv.setOnClickPendingIntent(R.id.tvTitleWidget, pendingIntent)

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

}
