package com.example.topnews.screens.readlater

import com.example.topnews.data.db.Article
import com.example.topnews.utils.ObservableData
import java.util.Observable
import java.util.Observer

class ReadLaterObservable : Observable() {
	private var lastShowCheckboxPar: Boolean? = false

	override fun addObserver(o: Observer) {
		super.addObserver(o)
		o.update(this, ObservableData(null, lastShowCheckboxPar))

	}

	fun notifyAll(articles: ArrayList<Article>? = null, showCheckbox: Boolean? = lastShowCheckboxPar) {
		showCheckbox.let { lastShowCheckboxPar = showCheckbox }
		setChanged()
		notifyObservers(ObservableData(articles, showCheckbox))
	}
}

interface ReadLaterObserver : Observer {

	override fun update(o: Observable, arg: Any?) {

		updateView(o, arg as ObservableData)
	}

	fun updateView(o: Observable, observedData: ObservableData)
}