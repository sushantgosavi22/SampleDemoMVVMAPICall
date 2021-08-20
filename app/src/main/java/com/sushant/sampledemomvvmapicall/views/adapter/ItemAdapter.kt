package com.sushant.sampledemomvvmapicall.views.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter<T, out H : BaseViewHolder<T>>(val list : ArrayList<T>, private var listener: IAdapterItemListener<T>) :  RecyclerView.Adapter<BaseViewHolder<T>>() {

    override fun getItemCount(): Int =list.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> = listener.getHolder(parent)
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        list[position]?.let { (holder as H).bind(it, position, listener) }
    }

    fun setList(mList : ArrayList<T>){
        list.clear()
        list.addAll(mList)
        notifyDataSetChanged()
    }

    interface IAdapterItemListener<T> {
        fun onItemClick(pos: Int, data: T?)
        fun getHolder(parent: ViewGroup):BaseViewHolder<T>
    }
}