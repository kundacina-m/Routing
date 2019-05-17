package base

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable

abstract class BaseDataSource<Item> : PageKeyedDataSource<Int, Item>() {

	protected val disposables by lazy {
		CompositeDisposable()
	}

	protected var currentPage: Int = 1
	protected var nextPage: Int = 2

	abstract fun initialLoad(callback: LoadInitialCallback<Int, Item>)

	abstract fun onScrollLoad(callback: LoadCallback<Int, Item>)

	override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {
		handlePages()
		initialLoad(callback)
	}

	override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
		handlePages(params.key)
		onScrollLoad(callback)
	}

	override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {}

	private fun handlePages(current: Int = 1) {
		currentPage = current
		nextPage = currentPage + 1
	}

	// Added for clearing disposables
	override fun addInvalidatedCallback(onInvalidatedCallback: InvalidatedCallback) {
		super.addInvalidatedCallback(onInvalidatedCallback)
		disposables.clear()
	}
}