package base

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BasePagedListAdapter<T>(diff: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, ViewHolder>(diff) {

	var clickListener: ((T) -> Unit?)? = null

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		getItem(position)?.let { (holder as ViewHolderAdapterBinder<T>).bind(it) }
	}

	internal interface ViewHolderAdapterBinder<T> {
		fun bind(dataItem: T)
	}

	interface OnItemClickListener<in T> {
		fun onItemClick(dataItem: T)
	}
}