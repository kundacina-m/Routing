package com.example.topnews.screens.categories

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import base.BaseViewHolder
import com.example.topnews.domain.WrappedResponse.OnError
import com.example.topnews.screens.categories.itemsincategory.ItemsAdapter
import kotlinx.android.synthetic.main.item_section.view.progressBar
import kotlinx.android.synthetic.main.item_section.view.rvInCategories
import kotlinx.android.synthetic.main.item_section.view.tvCategory
import kotlinx.android.synthetic.main.item_section.view.tvError
import kotlin.properties.Delegates

class CategoriesViewHolder(itemView: View) :
	BaseViewHolder<String>(itemView) {

	private val itemsAdapter by lazy {
		ItemsAdapter()
	}

	lateinit var callback: (ItemsAdapter, String) -> Unit

	var category by Delegates.observable("", { _, _, receivedCategory ->
		callback.invoke(itemsAdapter, receivedCategory)
	})

	init {
		itemView.rvInCategories.apply {
			layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
			this.adapter = itemsAdapter
		}
	}

	override fun bind(dataItem: String) {
		category = dataItem
		itemView.tvCategory.text = dataItem

	}

	fun updateView(error: OnError<Nothing>? = null) {
		itemView.progressBar.hide()
		error?.let {
			itemView.tvError.apply {
				text = it.error.toString()
				visibility = View.VISIBLE
			}
		}
	}

	interface InitializedViewHolder {
		fun initializedCallback(adapter: ItemsAdapter, category: String)
	}

}