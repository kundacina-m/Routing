package base

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

abstract class BaseDataSourceFactory<Item> : DataSource.Factory<Int, Item>() {

	val dataSource = MutableLiveData<DataSource<Int,Item>>()

	abstract fun initDataSource(): DataSource<Int,Item>

	override fun create(): DataSource<Int, Item> {
		val source = initDataSource()
		dataSource.postValue(source)
		return source
	}
}