package com.example.topnews.widget

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.topnews.R
import com.example.topnews.screens.frame.FrameActivity

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [ReadLaterWidgetConfigureActivity]
 */
class ReadLaterWidget : AppWidgetProvider() {

	override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
		// There may be multiple widgets active, so update all of them

		WidgetRefresher.widgetIds = appWidgetIds
		for (appWidgetId in appWidgetIds) {
			val views = RemoteViews(
				context.packageName,
				R.layout.read_later_widget
			)

			// Start factory
			val intent = Intent(context, WidgetRemoteViewService::class.java)
			views.setRemoteAdapter(R.id.listViewWidget, intent)

			// Take fillInIntents and GO
			val builder = TaskStackBuilder.create(context)
				.addNextIntent(Intent(context, FrameActivity::class.java))
			views.setPendingIntentTemplate(
				R.id.listViewWidget,
				builder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
			)

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

		super.onReceive(context, intent)
	}

	companion object {

		internal fun updateAppWidget(
			context: Context, appWidgetManager: AppWidgetManager,
			appWidgetId: Int
		) {

			val widgetText =
				ReadLaterWidgetConfigureActivity.loadTitlePref(context, appWidgetId)
			// Construct the RemoteViews object
			val views = RemoteViews(context.packageName, R.layout.read_later_widget)
			views.setTextViewText(R.id.appwidget_text, widgetText)

			// Instruct the widget manager to update the widget
			appWidgetManager.updateAppWidget(appWidgetId, views)
		}

	}

	object WidgetRefresher {

		lateinit var widgetIds: IntArray

		fun sendRefreshBroadcast(context: Context) {
			val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
			intent.component = ComponentName(context, ReadLaterWidget::class.java!!)
			context.sendBroadcast(intent)
		}
	}

}

