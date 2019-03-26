package com.example.topnews.screens.readlater

import com.example.topnews.screens.Article
import com.example.topnews.utils.ObservableData
import java.util.*

class ReadLaterObservable :Observable(){
    private var lastCheckAllPar: Boolean? = null
    private var lastShowCheckboxPar: Boolean? = false

    override fun addObserver(o: Observer) {
        super.addObserver(o)
            o.update(this, ObservableData(null,lastCheckAllPar, lastShowCheckboxPar))
    }

    fun notifyAll(article: Article? = null, checkAll: Boolean? = null, showCheckbox: Boolean? = lastShowCheckboxPar){
        lastCheckAllPar = checkAll
        showCheckbox.let { lastShowCheckboxPar = showCheckbox }
        setChanged()
        notifyObservers(ObservableData(article,lastCheckAllPar,showCheckbox))
    }
}


interface ReadLaterObserver:Observer{

    override fun update(o: Observable, arg: Any?) {

        updateView(o,arg as ObservableData)
    }

    fun updateView(o: Observable, observedData: ObservableData)
}