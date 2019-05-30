package com.example.topnews.utils

class OnItemClickedBus<Type> {

	var callback: ((Type) -> Unit)? = null

	fun subscribe(onNotify: (Any?) -> Unit) {
		callback = onNotify
	}

	fun post(event: Type) =
		callback?.invoke(event)

	fun clear() {
		callback = null
	}

}
