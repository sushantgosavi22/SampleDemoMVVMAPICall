package com.sushant.sampledemomvvmapicall.views.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter<T, out H : BaseViewHolder<T>>(val list : ArrayList<T>, private var listener: IAdapterItemListener<T>) :  RecyclerView.Adapter<BaseViewHolder<T>>() {

    var selectedPos: Int =0
    override fun getItemCount(): Int =list.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> = listener.getHolder(parent)
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        list[position]?.let { (holder as H).bind(it, position, listener,position==selectedPos) }
    }

    fun setList(newList : ArrayList<T>,mSelectedPos: Int){
        selectedPos = mSelectedPos
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    interface IAdapterItemListener<T> {
        fun onItemClick(pos: Int, data: T?)
        fun getHolder(parent: ViewGroup):BaseViewHolder<T>
    }
}