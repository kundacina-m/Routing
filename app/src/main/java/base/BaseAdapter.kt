package base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private var data: List<T> = emptyList()

	var oneClickListener: ((T) -> Unit?)? = null

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
		(holder as ViewHolderAdapterBinder<T>).bind(data[position])

	override fun getItemCount(): Int = data.size

	fun setData(dataList: List<T>) {
		notifyChanged(data, dataList)
		data = dataList
	}

	fun getData(): List<T> = data

	fun getItemOnPosition(position: Int): T = data[position]

	fun getItemsFromTo(from: Int, to: Int): List<T> = data.subList(from, to)

	private fun notifyChanged(old: List<T>, new: List<T>) = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

		override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
			old[oldItemPosition].hashCode() == new[newItemPosition].hashCode()

		override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
			old[oldItemPosition] == new[newItemPosition]

		override fun getOldListSize() = old.size
		override fun getNewListSize() = new.size

	}).dispatchUpdatesTo(this)

	internal interface ViewHolderAdapterBinder<T> {
		fun bind(dataItem: T)
	}

	interface OnItemClickListener<in T> {
		fun onItemClick(dataItem: T)
	}

}