package com.example.topnews.data.db

import com.example.topnews.domain.WrappedResponse
import io.reactivex.Flowable

interface BaseDao<T> {

	fun addItem(item: T): Flowable<WrappedResponse<Boolean>>
	fun deleteItem(item: T): Flowable<WrappedResponse<Boolean>>
	fun changeItem(item: T): Flowable<WrappedResponse<Boolean>>
	fun getAllItems(): Flowable<WrappedResponse<ArrayList<T>>>

}
//
//class Execute<T>(private val function: KCallable<T>, vararg arguments: Any?) {
//
//	private val map by lazy { hashMapOf<KParameter, Any?>() }
//
//	init {
//		for ((counter, arg) in arguments.withIndex()) {
//			map[function.parameters[counter]] = arg
//		}
//	}
//
//	fun execute(): T = function.callBy(map)
//
//	fun executeAsync(callback: (T) -> Unit) = ExecuteAsync(map, function, callback).execute()!!
//
//	class ExecuteAsync<T>(private val map: HashMap<KParameter, Any?>, private val function: KCallable<T>,
//		private val callback: (T) -> Unit) :
//		AsyncTask<Void, Void, T>() {
//
//		override fun doInBackground(vararg arg: Void): T = function.callBy(map)
//
//		override fun onPostExecute(result: T) {
//			super.onPostExecute(result)
//			callback.invoke(result)
//		}
//
//	}
//
//}

