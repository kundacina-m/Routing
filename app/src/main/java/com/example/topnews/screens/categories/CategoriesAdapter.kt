package com.example.topnews.screens.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import base.BaseAdapter
import com.example.topnews.R
import com.example.topnews.domain.WrappedResponse.OnError
import kotlinx.android.synthetic.main.item_section.view.tvCategory

class CategoriesAdapter : BaseAdapter<String>(), CategoriesViewHolder.InitializedViewHolder {

	var handleLiveData: ((String, ItemsAdapter) -> Unit)? = null

	var viewHolders = hashMapOf<String, CategoriesViewHolder>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		CategoriesViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.item_section, parent,
				false
			)
		).apply {
			callback = this@CategoriesAdapter::initializedCallback
			viewHolders[getItemOnPosition(viewType)] = this
		}

	override fun getItemViewType(position: Int): Int {
		return position
	}

	fun notifyDataArrived(category: String, response: OnError<Nothing>? = null) {
		viewHolders[category]?.updateView(response)
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		super.onBindViewHolder(holder, position)

		holder.itemView.tvCategory.setOnClickListener {
			clickListener?.invoke(getItemOnPosition(position))

		}
	}

	override fun initializedCallback(adapter: ItemsAdapter, category: String) {
		handleLiveData?.invoke(category, adapter)
	}

	interface BindAdapterToLiveData {
		fun bindAdapterToLiveData(category: String, adapter: ItemsAdapter)
	}
}