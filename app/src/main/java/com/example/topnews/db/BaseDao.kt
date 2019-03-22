package com.example.topnews.db

import android.os.AsyncTask
import kotlin.reflect.*


interface BaseDao<T> {

    fun insertItem(item: T): Execute<Boolean>
    fun removeItem(item: T): Execute<Boolean>
    fun updateItem(item: T): Execute<Boolean>
    fun getAllItems(): Execute<List<T>>

}

class Execute<T>(private val function: KCallable<T>, vararg arguments: Any?) {

    private val map by lazy { hashMapOf<KParameter, Any?>() }

    init {
        for ((counter, arg) in arguments.withIndex()) {
            map[function.parameters[counter]] = arg
        }
    }

    fun execute(): T = function.callBy(map)

    fun executeAsync(callback: (T) -> Unit) = ExecuteAsync(map, function, callback).execute()!!


    class ExecuteAsync<T>(private val map: HashMap<KParameter,Any?>, private val function: KCallable<T>, private val callback: (T) -> Unit) :
        AsyncTask<Void, Void, T>() {

        override fun doInBackground(vararg arg: Void): T = function.callBy(map)

        override fun onPostExecute(result: T) {
            super.onPostExecute(result)
            callback.invoke(result)
        }

    }

}

