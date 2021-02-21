package com.sushant.sampledemomvvmapicall.views.adapter


import android.view.ViewGroup

class ItemAdapter<T, out H : BaseViewHolder<T>>( private var listener: IAdapterItemListener<T>) : BasePaginationAdapter<T>() {


    fun clearDataItems() {
        currentList?.clear()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> = listener.getHolder(parent)
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
       getItem(position)?.let { (holder as H).bind(it, position, listener) }
    }

    interface IAdapterItemListener<T> {
        fun onItemClick(pos: Int, data: T?)
        fun getHolder(parent: ViewGroup):BaseViewHolder<T>
    }
}