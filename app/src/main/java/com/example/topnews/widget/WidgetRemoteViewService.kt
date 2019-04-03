package com.example.topnews.widget

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetRemoteViewService : RemoteViewsService() {
	override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
		return MyWidgetRemoteViewsFactory(this.applicationContext)
	}
}
