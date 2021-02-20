package com.sushant.sampledemomvvmapicall.views.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ItemAdapter<T, out H : BaseViewHolder<T>>(private var listener: IAdapterItemListener<T>) :
        RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val itemList = ArrayList<T>()
    override fun getItemCount(): Int = itemList.size
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        (holder as H).bind(itemList[position], position, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> =listener.getHolder(parent)

    fun setList(list: List<T>) {
        this.itemList.clear()
        this.itemList.addAll(list)
        notifyDataSetChanged()
    }

    interface IAdapterItemListener<T> {
        fun onItemClick(pos: Int, data: T?)
        fun getHolder(parent: ViewGroup):BaseViewHolder<T>
    }
}