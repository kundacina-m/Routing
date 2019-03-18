package base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<T> = emptyList()

    var listener: ((T) -> Unit?)? = null

    @LayoutRes
    abstract fun getItemViewId(): Int

    abstract fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        getViewHolder(LayoutInflater.from(parent.context).inflate(getItemViewId(), parent, false), getItemViewId())

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderAdapterBinder<T>).bind(data[position])

        listener?.let {
            holder.itemView.setOnClickListener {
                listener?.invoke(getItemOnPosition(position))
            }
        }
    }

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
            old[oldItemPosition]!! === new[newItemPosition]

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