package com.example.topnews.screens.categories.seemore

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.topnews.data.db.Article
import com.example.topnews.utils.OnItemClickedBus

object CategoryModule : LifecycleObserver {

	lateinit var articleClick: OnItemClickedBus<Article>
		private set

	lateinit var categoryClick: OnItemClickedBus<String>
		private set

	fun registerLifecycle(lifecycle: Lifecycle) =
		lifecycle.addObserver(this)

	@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
	fun onCreateFragment() {
		articleClick = OnItemClickedBus()
		categoryClick = OnItemClickedBus()

	}

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	fun onDestroyFragment() {
		articleClick.clear()
		categoryClick.clear()
	}
}