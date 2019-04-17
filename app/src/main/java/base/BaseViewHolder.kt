package base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView),
												   BaseAdapter.ViewHolderAdapterBinder<T>