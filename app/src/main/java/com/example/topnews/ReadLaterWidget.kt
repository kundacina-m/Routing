package com.example.topnews

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.content.Intent
import android.content.ComponentName
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.os.Bundle
import com.example.topnews.screens.Article
import com.example.topnews.screens.frame.FrameActivity
import com.example.topnews.utils.Constants








/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [ReadLaterWidgetConfigureActivity]
 */
class ReadLaterWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them

        readLaterProvider.widgetIds = appWidgetIds
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(
                context.packageName,
                R.layout.read_later_widget
            )

            // Start factory
            val intent = Intent(context, WidgetRemoteViewService::class.java)
            views.setRemoteAdapter(R.id.listViewWidget, intent)


            val builder = TaskStackBuilder.create(context)
                .addNextIntent(Intent(context, FrameActivity::class.java))
            views.setPendingIntentTemplate(
                R.id.listViewWidget,
                builder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
            )




//            val toastIntent = Intent(context, ReadLaterWidget::class.java)
//            toastIntent.action = "SHOW_DETAILS"
//            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
//            val toastPendingIntent = PendingIntent.getBroadcast(
//                context, 0, toastIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//            views.setPendingIntentTemplate(R.id.listViewWidget, toastPendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)


    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            ReadLaterWidgetConfigureActivity.deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        if (action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            // refresh all your widgets
            val mgr = AppWidgetManager.getInstance(context)
            val cn = ComponentName(context!!, ReadLaterWidget::class.java)
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.listViewWidget)
        }

        if (action == "SHOW_DETAILS"){
            val inte = Intent(context,FrameActivity::class.java)
            val article = intent.getParcelableExtra<Article>(Constants.PARCEL_FOR_ARTICLE_DETAILS)
            inte.putExtra(Constants.PARCEL_FOR_ARTICLE_DETAILS,intent.getParcelableExtra<Article>(Constants.PARCEL_FOR_ARTICLE_DETAILS))
            context?.startActivity(inte)

        }

        super.onReceive(context, intent)
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val widgetText = ReadLaterWidgetConfigureActivity.loadTitlePref(context, appWidgetId)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.read_later_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }



    }

    object readLaterProvider {

        lateinit var widgetIds: IntArray

        fun sendRefreshBroadcast(context: Context) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            intent.component = ComponentName(context, ReadLaterWidget::class.java!!)
            context.sendBroadcast(intent)
        }
    }

}

