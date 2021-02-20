package com.sushant.sampledemomvvmapicall.views.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(private var itemBinding: ViewDataBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    open fun bind(model: T, position: Int, listenerI: ItemAdapter.IAdapterItemListener<T>?) {
        itemBinding.apply {
            itemBinding.root.setOnClickListener {
                listenerI?.onItemClick(position, model)
            }
        }
    }
}